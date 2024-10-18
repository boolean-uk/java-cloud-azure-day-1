package com.booleanuk.things;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("things")
public class ThingController {
  @Autowired
  private ThingRepository repository;

  @GetMapping
  public ResponseEntity<List<Thing>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @PostMapping
  public ResponseEntity<Thing> post(@RequestBody Thing thing) {
    System.out.println(thing);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.repository.save(thing));
  }
}
