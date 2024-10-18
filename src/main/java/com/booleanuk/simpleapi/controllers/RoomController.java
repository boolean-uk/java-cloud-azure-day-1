package com.booleanuk.simpleapi.controllers;

import com.booleanuk.simpleapi.Repositories.RoomRepository;
import com.booleanuk.simpleapi.models.Penguin;
import com.booleanuk.simpleapi.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public List<Room> getAll() {
        return this.roomRepository.findAll();
    }
}
