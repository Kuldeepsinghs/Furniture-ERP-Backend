package com.furniture.FurnitureManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.ShowroomRequest;
import com.furniture.FurnitureManagement.entity.Showroom;
import com.furniture.FurnitureManagement.repository.ShowroomRepository;

@Service
public class ShowroomService {

    private final ShowroomRepository
            showroomRepository;

    public ShowroomService(
            ShowroomRepository showroomRepository) {

        this.showroomRepository =
                showroomRepository;
    }

    public Showroom addShowroom(
            ShowroomRequest request) {

        Showroom showroom =
                new Showroom();

        showroom.setName(
                request.getName());

        showroom.setPhone(
                request.getPhone());

        showroom.setAddress(
                request.getAddress());

        return showroomRepository.save(
                showroom);
    }

    public List<Showroom>
    getAllShowrooms() {

        return showroomRepository.findByActiveTrue();
    }

    public Showroom updateShowroom(
            Long id,
            ShowroomRequest request) {

        Showroom showroom =
                showroomRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Showroom not found"));

        showroom.setName(
                request.getName());

        showroom.setPhone(
                request.getPhone());

        showroom.setAddress(
                request.getAddress());

        return showroomRepository.save(
                showroom);
    }

    public Showroom deleteShowroom(
            Long id) {

        Showroom showroom =
                showroomRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Showroom not found"));

        showroom.setActive(false);

        return showroomRepository.save(
                showroom);
    }
}
