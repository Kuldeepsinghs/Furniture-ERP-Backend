package com.furniture.FurnitureManagement.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

    @Test
    void generatedTokenContainsRoleAuthorities() {

        JwtService jwtService =
                new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secret",
                "0123456789012345678901234567890123456789012345678901234567890123");

        UserDetails salesUser =
                User.withUsername("sales")
                        .password("password")
                        .roles("SALES")
                        .build();

        String token =
                jwtService.generateToken(salesUser);

        assertThat(jwtService.extractUsername(token))
                .isEqualTo("sales");

        assertThat(jwtService.extractRoles(token))
                .containsExactly("ROLE_SALES");

        assertThat(jwtService.validateToken(token, "sales"))
                .isTrue();
    }
}
