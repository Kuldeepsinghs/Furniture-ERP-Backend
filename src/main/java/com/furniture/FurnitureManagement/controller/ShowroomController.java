package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.FurnitureManagement.dto.ShowroomRequest;
import com.furniture.FurnitureManagement.entity.Showroom;
import com.furniture.FurnitureManagement.service.ShowroomService;

@RestController
@RequestMapping("/showrooms")
public class ShowroomController {

    private final ShowroomService
            showroomService;

    public ShowroomController(
            ShowroomService showroomService) {

        this.showroomService =
                showroomService;
    }

    @PostMapping
    public Showroom addShowroom(
            @RequestBody
            ShowroomRequest request) {

        return showroomService
                .addShowroom(request);
    }

    @GetMapping
    public List<Showroom>
    getAllShowrooms() {

        return showroomService
                .getAllShowrooms();
    }

    @PutMapping("/{id}")
    public Showroom updateShowroom(
            @PathVariable Long id,
            @RequestBody ShowroomRequest request) {

        return showroomService
                .updateShowroom(
                        id,
                        request);
    }

    @DeleteMapping("/{id}")
    public Showroom deleteShowroom(
            @PathVariable Long id) {

        return showroomService
                .deleteShowroom(id);
    }
}
