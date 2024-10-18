package com.booleanuk.library.controller;

import com.booleanuk.library.model.Author;
import com.booleanuk.library.model.Book;
import com.booleanuk.library.model.dto.BookDTO;
import com.booleanuk.library.repository.AuthorRepository;
import com.booleanuk.library.repository.BookRepository;
import com.booleanuk.library.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookRepository books;

    @Autowired
    private AuthorRepository authors;

    @GetMapping
    public ResponseEntity<BookListResponse> getAllBooks(){
        BookListResponse bookListResponse = new BookListResponse();
        bookListResponse.set(this.books.findAll());

        return new ResponseEntity<>(bookListResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        Book toReturn = this.books.findById(id).orElse(null);

        if (toReturn == null){
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        BookResponse bookResponse = new BookResponse();
        bookResponse.set(toReturn);

        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody BookDTO data){
        Author author = this.authors.findById(data.getAuthorId()).orElse(null);
        if(author == null){
            ErrorResponse error = new ErrorResponse("given author not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Book newBook = new Book();
        newBook.setAuthor(author); // TODO: validate given data?
        newBook.setGenre(data.getGenre());
        newBook.setTitle(data.getTitle());
        newBook.setDescription(data.getDescription());
        newBook.setRating(data.getRating());

        this.books.save(newBook);
        this.authors.save(author);

        BookResponse bookResponse = new BookResponse();
        bookResponse.set(newBook);;

        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Book toDelete = this.books.findById(id).orElse(null);
        if (toDelete == null){
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        BookResponse bookResponse = new BookResponse();
        bookResponse.set(toDelete);
        this.books.delete(toDelete);

        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody BookDTO newData){
        Book toUpdate = this.books.findById(id).orElse(null);
        if (toUpdate == null){
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        toUpdate.setRating(newData.getRating());
        toUpdate.setGenre(newData.getGenre());
        toUpdate.setTitle(newData.getTitle());
        toUpdate.setDescription(newData.getDescription());

        BookResponse bookResponse = new BookResponse();
        bookResponse.set(this.books.save(toUpdate));


        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

}
