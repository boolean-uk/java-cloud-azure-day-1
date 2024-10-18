package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.model.Developer;
import com.booleanuk.simpleapi.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("developers")
public class DeveloperController {
    @Autowired
    private final DeveloperRepository repository;

    public DeveloperController(DeveloperRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createDeveloper(@RequestBody Developer developer) {
        return new ResponseEntity<>(this.repository.save(developer),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable("id") Integer id,
                                                     @RequestBody Developer developer) {
        Developer developerToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing developer with that ID found")
        );
        developerToUpdate.setName(developer.getName());
        developerToUpdate.setLocation(developer.getLocation());


        return new ResponseEntity<>(this.repository.save(developerToUpdate),
                HttpStatus.CREATED);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Developer> deleteDeveloper(@PathVariable("id") Integer id) {
        Developer developerToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No developer with that ID were found")
        );
        this.repository.delete(developerToDelete);
        return ResponseEntity.ok(developerToDelete);
    }
}
