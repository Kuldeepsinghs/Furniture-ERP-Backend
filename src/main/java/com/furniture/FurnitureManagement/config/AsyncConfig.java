package com.furniture.FurnitureManagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Enables @Async so sending the sale-notification email happens on a
 * background thread and never slows down (or breaks) the actual
 * create-sale API response.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
}