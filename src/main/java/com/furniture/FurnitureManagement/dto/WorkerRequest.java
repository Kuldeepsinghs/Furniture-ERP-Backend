package com.furniture.FurnitureManagement.dto;

import java.time.LocalDate;

import com.furniture.FurnitureManagement.enums.WorkerRole;

public class WorkerRequest {

    private String name;

    private String phone;

    private WorkerRole role;

    private LocalDate joiningDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public WorkerRole getRole() {
		return role;
	}

	public void setRole(WorkerRole role) {
		this.role = role;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

    
    
}