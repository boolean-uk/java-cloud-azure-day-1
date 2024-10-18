package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.Exceptions.BadRequestException;
import com.booleanuk.simpleapi.Exceptions.NotFoundException;
import com.booleanuk.simpleapi.models.Book;
import com.booleanuk.simpleapi.payload.responses.ResponseDTO;
import com.booleanuk.simpleapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})

@RestController
@RequestMapping("books")
public class BooksController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Book> books = bookRepository.findAll();
        ResponseDTO<List<Book>> response = new ResponseDTO<>(
                "success",
                books);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book) {

        try {
            this.bookRepository.save(book);
            ResponseDTO<Book> response = new ResponseDTO<>("success", book);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        ResponseDTO<Book> response = new ResponseDTO<>("success", book);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublicationDate(book.getPublicationDate());
        bookToUpdate.setGenre(book.getGenre());

        try {
            bookToUpdate = this.bookRepository.save(bookToUpdate);
        } catch (Exception e) {
           throw new BadRequestException("bad request");
        }
        ResponseDTO<Book> response = new ResponseDTO<>("success", bookToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );
        this.bookRepository.delete(bookToDelete);
        ResponseDTO<Book> response = new ResponseDTO<>("success", bookToDelete);
        return ResponseEntity.ok(response);
    }
}
