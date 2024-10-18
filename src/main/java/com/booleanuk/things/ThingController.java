package com.booleanuk.things;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("things")
public class ThingController {
  @Autowired
  private ThingRepository repository;

  @GetMapping
  public ResponseEntity<List<Thing>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Thing> get(@PathVariable int id) {
    return ResponseEntity.ok(this.repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @PostMapping
  public ResponseEntity<Thing> post(@RequestBody Thing thing) {
    System.out.println(thing);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.repository.save(thing));
  }

  @PutMapping("{id}")
  public ResponseEntity<Thing> put(@PathVariable int id, @RequestBody Thing thing) {
    return ResponseEntity.ok(this.repository.findById(id)
        .map(existing -> {
          existing.setName(thing.getName());
          existing.setPrice(thing.getPrice());
          existing.setProducer(thing.getProducer());
          return existing;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Thing> delete(@PathVariable int id) {
    var deleted = this.repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    this.repository.deleteById(id);
    return ResponseEntity.ok(deleted);
  }
}