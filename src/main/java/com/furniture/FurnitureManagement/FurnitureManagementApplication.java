package com.furniture.FurnitureManagement;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FurnitureManagementApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(
				TimeZone.getTimeZone(
						"Asia/Kolkata"));
		SpringApplication.run(FurnitureManagementApplication.class, args);
	}

}
