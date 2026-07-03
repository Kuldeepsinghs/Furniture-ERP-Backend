package com.furniture.FurnitureManagement.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void setDefaultTimeZone() {

        TimeZone.setDefault(
                TimeZone.getTimeZone(
                        "Asia/Kolkata"));
    }
}
