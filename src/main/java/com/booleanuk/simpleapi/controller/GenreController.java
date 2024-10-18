package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.Exceptions.BadRequestException;
import com.booleanuk.simpleapi.Exceptions.NotFoundException;
import com.booleanuk.simpleapi.models.Genre;
import com.booleanuk.simpleapi.payload.responses.ResponseDTO;
import com.booleanuk.simpleapi.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genres")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Genre> genres = genreRepository.findAll();
        ResponseDTO<List<Genre>> response = new ResponseDTO<>(
                "success",
                genres);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Genre genre) {

        try {
            this.genreRepository.save(genre);
            ResponseDTO<Genre> response = new ResponseDTO<>("success", genre);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Genre genre = this.genreRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        ResponseDTO<Genre> response = new ResponseDTO<>("success", genre);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Genre genre) {
        Genre genreToUpdate = this.genreRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        genreToUpdate.setName(genre.getName());
        try {
            genreToUpdate = this.genreRepository.save(genreToUpdate);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
        ResponseDTO<Genre> response = new ResponseDTO<>("success", genreToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Genre genreToDelete = this.genreRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        this.genreRepository.delete(genreToDelete);
        ResponseDTO<Genre> response = new ResponseDTO<>("success", genreToDelete);
        return ResponseEntity.ok(response);
    }
}
