package com.booleanuk.simpleapi.controller;


import com.booleanuk.simpleapi.model.Videogame;
import com.booleanuk.simpleapi.model.Developer;
import com.booleanuk.simpleapi.repository.VideogameRepository;
import com.booleanuk.simpleapi.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("videogames")
public class VideogameController {
    @Autowired
    private final VideogameRepository repository;

    @Autowired
    private DeveloperRepository developerRepository;

    public VideogameController(VideogameRepository repository){
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
    public ResponseEntity<Object> createVideogame(@RequestBody Videogame videogame) {
        Developer developer = this.developerRepository.findById(videogame.getDeveloper().getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No developer found..")
        );
        videogame.setDeveloper(developer);

        return new ResponseEntity<>(this.repository.save(videogame),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Videogame> updateVideogame(@PathVariable("id") Integer id,
                                           @RequestBody Videogame videogame) {
        Videogame videogameToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing videogame with that ID found")
        );
        videogameToUpdate.setTitle(videogame.getTitle());
        videogameToUpdate.setRating(videogame.getRating());
        videogameToUpdate.setReleaseYear(videogame.getReleaseYear());

        Developer developer = this.developerRepository.findById(videogame.getDeveloper().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing developer with that ID found")
        );
        videogameToUpdate.setDeveloper(developer);


        return new ResponseEntity<>(this.repository.save(videogameToUpdate),
                HttpStatus.CREATED);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Videogame> deleteVideogame(@PathVariable("id") Integer id) {
        Videogame videogameToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No videogame with that ID were found")
        );
        this.repository.delete(videogameToDelete);
        return ResponseEntity.ok(videogameToDelete);
    }
}
