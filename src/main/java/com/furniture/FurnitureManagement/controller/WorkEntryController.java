package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.FurnitureManagement.dto.WorkEntryRequest;
import com.furniture.FurnitureManagement.entity.WorkEntry;
import com.furniture.FurnitureManagement.service.WorkEntryService;


	@RestController
	@RequestMapping("/work-entries")
	public class WorkEntryController {

	    private final WorkEntryService
	            service;

	    public WorkEntryController(
	            WorkEntryService service) {

	        this.service = service;
	    }

	    @PostMapping
	    public WorkEntry addWorkEntry(
	            @RequestBody
	            WorkEntryRequest request) {

	        return service.addWorkEntry(
	                request);
	    }

	    @GetMapping
	    public List<WorkEntry>
	    getAllEntries() {

	        return service.getAllEntries();
	    }
	    
	    @GetMapping("/ready-items")
	    public List<WorkEntry>
	    getReadyItems() {

	        return service
	        		.getReadyItems();
	    }

	    @GetMapping("/available-batches")
	    public List<WorkEntry>
	    getAvailableBatches() {

	        return service
	        		.getAvailableBatches();
	    }
	    
	    @PutMapping("/{id}")
	    public WorkEntry updateWorkEntry(
	            @PathVariable Long id,
	            @RequestBody WorkEntryRequest request) {

	        return service
	                .updateWorkEntry(
	                        id,
	                        request);
	    }

	    @PatchMapping("/{id}/cancel")
	    public WorkEntry cancelWorkEntry(
	            @PathVariable Long id) {

	        return service
	                .cancelWorkEntry(id);
	    }
	}
