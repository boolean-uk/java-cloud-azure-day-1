package com.booleanuk.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping
  public ResponseEntity<Producer> post(@RequestBody Producer thing) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.repository.save(thing));
  }
}
