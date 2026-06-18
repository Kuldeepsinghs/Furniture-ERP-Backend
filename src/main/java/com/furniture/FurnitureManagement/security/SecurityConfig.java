package com.furniture.FurnitureManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter
            jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config
                .getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain
    securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
        		.cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(
                        auth -> auth
                        
		                        .requestMatchers(
		                                "/auth/**")
		                        .permitAll()

		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/workers/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")
		
		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/workers/**")
		                        .hasRole("ADMIN")

		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/categories/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")

		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/categories/**")
		                        .hasRole("ADMIN")
		                        
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/designs/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")

		                        
		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/designs/**")
		                        .hasRole("ADMIN")
		                        
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/rate-types/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")

		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/rate-types/**")
		                        .hasRole("ADMIN")
		                        
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/product-rates/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")

		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/product-rates/**")
		                        .hasRole("ADMIN")
		                        
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/work-entries/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")

		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/work-entries/**")
		                        .hasRole("ADMIN")
		                        
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/payments/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")

		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/payments/**")
		                        .hasRole("ADMIN")
		                        
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/showrooms/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")

		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/showrooms/**")
		                        .hasRole("ADMIN")
		                        
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/ready-stock/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/shipments/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")

		                        .requestMatchers(
		                                HttpMethod.POST,
		                                "/shipments/**")
		                        .hasRole("ADMIN")
		                        
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/dashboard/**")
		                        .hasAnyRole(
		                                        "ADMIN",
		                                        "VIEWER")
		                        
		                        .requestMatchers(
		                                HttpMethod.GET,
		                                "/reports/**")
		                        .hasAnyRole(
		                                "ADMIN",
		                                "VIEWER")
		                        
		                        .requestMatchers(
		                                HttpMethod.PUT,
		                                "/work-entries/**")
		                        .hasRole("ADMIN")
		                        
		                        .requestMatchers(
		                                HttpMethod.PUT,
		                                "/payments/**")
		                        .hasRole("ADMIN")
		                        
		                        .requestMatchers(
		                                HttpMethod.PUT,
		                                "/product-rates/**")
		                        .hasRole("ADMIN")
		                        
		                        
                                .anyRequest()
                                .authenticated())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}