package com.furniture.FurnitureManagement.sales.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * One-time data migration: category used to live only on the parent
 * showroom_sales row. Now each product line (showroom_sale_products) has
 * its own category, so a customer's sale can mix categories (e.g. a sofa
 * and a cot in the same visit). This backfills existing product rows that
 * don't have a category yet by copying their parent sale's category, per
 * the agreed migration plan. It's safe to run on every startup - rows that
 * already have a category are left untouched.
 */
@Component
@Order(1)
public class SaleProductCategoryMigration
        implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(
                    SaleProductCategoryMigration.class);

    private final JdbcTemplate jdbcTemplate;

    public SaleProductCategoryMigration(
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {

        try {

            int updated =
                    jdbcTemplate.update(
                            """
                            UPDATE showroom_sale_products p
                            SET category = s.category
                            FROM showroom_sales s
                            WHERE p.sale_id = s.id
                              AND (p.category IS NULL OR TRIM(p.category) = '')
                              AND s.category IS NOT NULL
                            """);

            if (updated > 0) {

                log.info(
                        "Backfilled category on {} existing sale product line(s) "
                        + "from their parent sale.",
                        updated);
            }

        } catch (Exception ex) {

            log.error(
                    "Failed to backfill sale product categories: {}",
                    ex.getMessage(),
                    ex);
        }
    }
}