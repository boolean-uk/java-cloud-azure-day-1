package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.Exceptions.BadRequestException;
import com.booleanuk.simpleapi.Exceptions.NotFoundException;
import com.booleanuk.simpleapi.models.Author;
import com.booleanuk.simpleapi.payload.responses.ResponseDTO;
import com.booleanuk.simpleapi.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Author> authors = authorRepository.findAll();
        ResponseDTO<List<Author>> response = new ResponseDTO<>(
                "success",
                authors);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Author author) {

        try {
            this.authorRepository.save(author);
            ResponseDTO<Author> response = new ResponseDTO<>("success", author);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Author author = this.authorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        ResponseDTO<Author> response = new ResponseDTO<>("success", author);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        authorToUpdate.setName(author.getName());
        try {
            authorToUpdate = this.authorRepository.save(authorToUpdate);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
        ResponseDTO<Author> response = new ResponseDTO<>("success", authorToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Author authorToDelete = this.authorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        this.authorRepository.delete(authorToDelete);
        ResponseDTO<Author> response = new ResponseDTO<>("success", authorToDelete);
        return ResponseEntity.ok(response);
    }
}
