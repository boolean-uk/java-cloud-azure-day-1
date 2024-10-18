package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.dto.GenreDTO;
import com.booleanuk.simpleapi.model.Genre;
import com.booleanuk.simpleapi.repository.GenreRepository;
import com.booleanuk.simpleapi.responses.ErrorResponse;
import com.booleanuk.simpleapi.responses.GenreListResponse;
import com.booleanuk.simpleapi.responses.GenreResponse;
import com.booleanuk.simpleapi.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genres")
@CrossOrigin(origins = "http://localhost:5173")
public class GenreController {

    @Autowired
    GenreRepository repository;

    @GetMapping()
    public ResponseEntity<Response<?>> getAll() {
        List<Genre> genreList = ResponseEntity.ok(this.repository.findAll()).getBody();
        GenreListResponse genreListResponse = new GenreListResponse();
        genreListResponse.set(genreList);
        return ResponseEntity.ok(genreListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable Integer id) {
        Genre genre = this.repository.findById(id).orElse(null);
        if(genre == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Genre cannot be found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(response(genre));
    }

    @PostMapping()
    public ResponseEntity<Response<Genre>> create(@RequestBody GenreDTO genreDTO) {
        Genre genre = new Genre(
                genreDTO.getGenre()
        );
        this.repository.save(genre);
        return ResponseEntity.ok(response(genre));
    }

    public GenreResponse response(Genre genre) {
        GenreResponse genreResponse = new GenreResponse();
        genreResponse.set(genre);
        return genreResponse;
    }
}
