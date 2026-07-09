package com.furniture.FurnitureManagement.security;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private static final Logger log =
            LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    private final UserDetailsService
            userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService =
                userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException,
            IOException {

        String authHeader =
                request.getHeader(
                        "Authorization");

        if (authHeader == null
                || !authHeader.startsWith(
                        "Bearer ")) {

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        String token =
                authHeader.substring(7);

        try {

            String username =
                    jwtService.extractUsername(
                            token);

            if (username != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService
                        .loadUserByUsername(
                                username);

                if (jwtService.validateToken(
                        token,
                        userDetails.getUsername())) {

                    List<SimpleGrantedAuthority> authorities =
                            jwtService.extractRoles(token)
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    authorities.isEmpty()
                                    ? userDetails.getAuthorities()
                                    : authorities);

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authToken);
                }
            }

        } catch (JwtException | IllegalArgumentException ex) {

            // An expired, malformed, or otherwise invalid token should
            // just be treated as "not authenticated" - it must NOT crash
            // the request. Letting this propagate uncaught used to produce
            // a bare 403 "Access Denied" for ANY request (including
            // /auth/login, since the frontend always attaches whatever
            // token happens to be in storage) whenever an old token was
            // still saved in the browser.
            log.warn(
                    "Ignoring invalid/expired JWT on {}: {}",
                    request.getRequestURI(),
                    ex.getMessage());

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(
                request,
                response);
    }
}