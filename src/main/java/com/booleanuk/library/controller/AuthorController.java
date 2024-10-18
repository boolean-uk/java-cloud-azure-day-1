package com.booleanuk.library.controller;

import com.booleanuk.library.model.Author;
import com.booleanuk.library.model.dto.AuthorDTO;
import com.booleanuk.library.repository.AuthorRepository;
import com.booleanuk.library.responses.AuthorListResponse;
import com.booleanuk.library.responses.AuthorResponse;
import com.booleanuk.library.responses.ErrorResponse;
import com.booleanuk.library.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authors;

    @GetMapping
    public ResponseEntity<AuthorListResponse> getAll(){
        AuthorListResponse authorListResponse = new AuthorListResponse();
        authorListResponse.set(this.authors.findAll());
        return new ResponseEntity<>(authorListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody AuthorDTO data){
        Author newAuthor = new Author();
        newAuthor.setFirstName(data.getFirstName());
        newAuthor.setLastName(data.getLastName());

        this.authors.save(newAuthor);

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.set(newAuthor);

        return new ResponseEntity<>(authorResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id) {
        Author author = this.authors.findById(id).orElse(null);
        if (author == null){
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.set(author);

        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Author toDelete = this.authors.findById(id).orElse(null);
        if (toDelete == null){
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.set(toDelete);
        this.authors.delete(toDelete);
        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateAuthor(@PathVariable int id, @RequestBody AuthorDTO newData) {
        Author toUpdate = this.authors.findById(id).orElse(null);
        if (toUpdate == null){
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        toUpdate.setFirstName(newData.getFirstName());
        toUpdate.setLastName(newData.getLastName());

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.set(this.authors.save(toUpdate));

        return new ResponseEntity<>(authorResponse, HttpStatus.CREATED);
    }

}
