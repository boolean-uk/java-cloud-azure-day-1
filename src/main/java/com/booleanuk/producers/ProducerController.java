package com.booleanuk.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("producers")
public class ProducerController {
  @Autowired
  private ProducerRepository repository;

  @GetMapping
  public ResponseEntity<List<Producer>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Producer> get(@PathVariable int id) {
    return ResponseEntity.ok(this.repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @PostMapping
  public ResponseEntity<Producer> post(@RequestBody Producer thing) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.repository.save(thing));
  }

  @PutMapping("{id}")
  public ResponseEntity<Producer> put(@PathVariable int id, @RequestBody Producer thing) {
    return ResponseEntity.ok(this.repository.findById(id)
        .map(existing -> {
          existing.setName(thing.getName());
          existing.setThings(thing.getThings());
          return existing;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Producer> delete(@PathVariable int id) {
    var deleted = this.repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    this.repository.deleteById(id);
    return ResponseEntity.ok(deleted);
  }
}