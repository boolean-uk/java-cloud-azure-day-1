package com.booleanuk.simpleapi.controllers;

import com.booleanuk.simpleapi.models.Studio;
import com.booleanuk.simpleapi.repositories.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("studio")
public class StudioController {
    @Autowired
    private StudioRepository studioRepository;

    @PostMapping
    public ResponseEntity<Studio> create(@RequestBody Studio studio){
        this.studioRepository.save(studio);
        return new ResponseEntity<>(studio, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Studio> getAll(){
        return this.studioRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Studio> getOne(@PathVariable int id){
        Studio studio = this.studioRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No studio with that id")
        );
        return ResponseEntity.ok(studio);
    }

    @PutMapping("{id}")
    public ResponseEntity<Studio> update(@PathVariable int id, @RequestBody Studio studio){
        Studio studioToUpdate = this.studioRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No studio with that id")
        );
        studioToUpdate.setStudioName(studio.getStudioName());
        return new ResponseEntity<>(this.studioRepository.save(studioToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Studio> delete(@PathVariable int id){
        Studio studio = this.studioRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No studio with that id")
        );
        this.studioRepository.delete(studio);
        return ResponseEntity.ok(studio);
    }
}
