package com.booleanuk.simpleapi.controllers;

import com.booleanuk.simpleapi.Repositories.PenguinRepository;
import com.booleanuk.simpleapi.models.Penguin;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("penguins")
public class PenguinController {

    @Autowired
    private PenguinRepository penguinRepository;

    @GetMapping
    public List<Penguin> getAll() {
        return this.penguinRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Penguin> getById(@PathVariable int id) {
        Penguin penguin = this.penguinRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(penguin);
    }


    @PostMapping
    public ResponseEntity<Penguin> createPenguin(@RequestBody Penguin penguin) {
        return new ResponseEntity<Penguin>(this.penguinRepository.save(penguin), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Penguin> updatePenguin(@PathVariable int id, @RequestBody Penguin penguinBody) {
        Penguin penguinToUpdate = this.penguinRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        penguinToUpdate.setUsername(penguinBody.getUsername());
        penguinToUpdate.setFirstAppearance(penguinBody.getFirstAppearance());
        penguinToUpdate.setMostRecentGiveaway(penguinBody.getMostRecentGiveaway());
        penguinToUpdate.setMeetable(penguinBody.getMeetable());
        penguinToUpdate.setVisitor(penguinBody.getVisitor());
        penguinToUpdate.setVillainous(penguinBody.getVillainous());

        return new ResponseEntity<Penguin>(this.penguinRepository.save(penguinToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Penguin> deletePenguin(@PathVariable int id) {
        Penguin penguinToDelete = this.penguinRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        this.penguinRepository.delete(penguinToDelete);
        return ResponseEntity.ok(penguinToDelete);
    }

    //Custom get endpoints
    @GetMapping("/villainous")
    public List<Penguin> findVillainousPenguins() {
        return this.penguinRepository.findAllByVillainousTrue();
    }

    @GetMapping("/visitors")
    public List<Penguin> findVisitorPenguins() {
        return this.penguinRepository.findAllByVisitorTrue();
    }

    @GetMapping("/meetable")
    public List<Penguin> findMeetablePenguins() {
        return this.penguinRepository.findAllByMeetableTrue();
    }
}
