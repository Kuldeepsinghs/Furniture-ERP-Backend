package com.furniture.FurnitureManagement.entity;

import java.time.LocalDate;

import com.furniture.FurnitureManagement.enums.Status;
import com.furniture.FurnitureManagement.enums.WorkerRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "workers")
public class Worker {

	@Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,
            unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private WorkerRole role;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate joiningDate;

    public Worker() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Worker(Long id, String name, String phone, WorkerRole role, Status status, LocalDate joiningDate) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.role = role;
		this.status = status;
		this.joiningDate = joiningDate;
	}

    
}
