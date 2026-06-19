package com.furniture.FurnitureManagement.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.furniture.FurnitureManagement.entity.User;
import com.furniture.FurnitureManagement.enums.Status;
import com.furniture.FurnitureManagement.enums.UserRole;
import com.furniture.FurnitureManagement.repository.UserRepository;

@Component
public class DataInitializer
        implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();

            admin.setUsername("admin");

            admin.setPassword(
                    passwordEncoder.encode("admin123"));

            admin.setRole(UserRole.ADMIN);

            admin.setStatus(Status.ACTIVE);

            userRepository.save(admin);
        }

        if (userRepository.findByUsername("viewer").isEmpty()) {

            User viewer = new User();

            viewer.setUsername("viewer");

            viewer.setPassword(
                    passwordEncoder.encode("furniture@2026"));

            viewer.setRole(UserRole.VIEWER);

            viewer.setStatus(Status.ACTIVE);

            userRepository.save(viewer);
        }
    }
}