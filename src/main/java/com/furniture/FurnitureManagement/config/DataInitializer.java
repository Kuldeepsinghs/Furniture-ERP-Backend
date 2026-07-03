package com.furniture.FurnitureManagement.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.furniture.FurnitureManagement.entity.User;
import com.furniture.FurnitureManagement.enums.Status;
import com.furniture.FurnitureManagement.enums.UserRole;
import com.furniture.FurnitureManagement.repository.UserRepository;

@Component
public class DataInitializer
        implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JdbcTemplate jdbcTemplate) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        boolean salesRoleAllowed =
                updateUserRoleConstraint();

        if (!salesRoleAllowed) {
            log.error(
                    "Could not update the 'users_role_check' constraint to "
                    + "allow the SALES role. The 'sales' demo account will "
                    + "NOT be created until this is fixed. Check the "
                    + "database user's ALTER TABLE privileges and the "
                    + "constraint name.");
        }

        if (userRepository.findByUsername("sales").isPresent()) {
            User existingSales =
                    userRepository.findByUsername("sales").get();
            log.info(
                    "Existing 'sales' user found - role={}, status={}",
                    existingSales.getRole(),
                    existingSales.getStatus());
        }

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

        if (salesRoleAllowed
                && userRepository.findByUsername("sales").isEmpty()) {

            User sales = new User();

            sales.setUsername("sales");

            sales.setPassword(
                    passwordEncoder.encode("sales@2026"));

            sales.setRole(UserRole.SALES);

            sales.setStatus(Status.ACTIVE);

            userRepository.save(sales);
        }
    }

    private boolean updateUserRoleConstraint() {

        try {

            jdbcTemplate.execute(
                    "ALTER TABLE users DROP CONSTRAINT IF EXISTS users_role_check");

            jdbcTemplate.execute(
                    """
                    ALTER TABLE users
                    ADD CONSTRAINT users_role_check
                    CHECK (role IN ('ADMIN','VIEWER','SALES'))
                    """);

            return true;
        } catch (Exception ex) {

            log.error(
                    "Failed to update users_role_check constraint: {}",
                    ex.getMessage(),
                    ex);

            return false;
        }
    }
}