package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.model.*;
import com.booleanuk.simpleapi.repository.BorrowRecordRepository;
import com.booleanuk.simpleapi.repository.BorrowableRepository;
import com.booleanuk.simpleapi.repository.UserRepository;
import com.booleanuk.simpleapi.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("borrowables")
public class Controller {
    @Autowired
    private BorrowableRepository repository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("book")
    public ResponseEntity<ApiResponse<?>> createBook (@RequestBody Book bookDetails) {
        if (!bookDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified book, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Book book = new Book(bookDetails.getTitle(), bookDetails.getAuthor(), bookDetails.getPublisher(), bookDetails.getGenre());

        ApiResponse<Book> response = new ApiResponse<>("success", repository.save(book));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("game")
    public ResponseEntity<ApiResponse<?>> createGame (@RequestBody Game gameDetails) {
        if (!gameDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified game, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Game game = new Game(gameDetails.getTitle(), gameDetails.getPublisher(), gameDetails.getGenre(), gameDetails.getReleaseYear());

        ApiResponse<Game> response = new ApiResponse<>("success", repository.save(game));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/borrow/{itemId}")
    public ResponseEntity<ApiResponse<?>> borrowItem(@PathVariable int userId, @PathVariable int itemId) {
        Optional<User> use = this.userRepository.findById(userId);
        if (use.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No user was found matching your credentials, id: %d", userId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        User user = use.get();

        Optional<Borrowable> bor = this.repository.findById(itemId);
        if (bor.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No item with id %d found.", itemId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Borrowable borrowable = bor.get();
        if (!borrowable.isAvailable()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("Item with id %d is currently borrowed already.", itemId));
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        borrowable.setBorrower(user);

        BorrowRecord record = new BorrowRecord(user, borrowable);

        this.repository.save(borrowable);
        this.borrowRecordRepository.save(record);

        ApiResponse<Borrowable> response = new ApiResponse<>("success", borrowable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}/return/{itemId}")
    public ResponseEntity<ApiResponse<?>> returnItem(@PathVariable int userId, @PathVariable int itemId) {
        Optional<User> use = this.userRepository.findById(userId);
        if (use.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No user was found matching your credentials, id: %d", userId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Borrowable> bor = this.repository.findById(itemId);
        if (bor.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No item with id %d found.", itemId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Borrowable borrowable = bor.get();
        borrowable.setBorrower(null);

        BorrowRecord record;
        try {
            record = borrowable.getBorrowHistory().getLast();
        } catch (NoSuchElementException e) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("The item with id %d isn't currently borrowed.", itemId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        record.setReturnedAt(LocalDateTime.now());

        this.repository.save(borrowable);
        this.borrowRecordRepository.save(record);

        ApiResponse<Borrowable> response = new ApiResponse<>("success", borrowable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("user")
    public ResponseEntity<ApiResponse<?>> postUser(@RequestBody User userDetails) {
        if (!userDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified user, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = new User(userDetails.getUsername(), userDetails.getEmail(), userDetails.getPassword());

        ApiResponse<User> response = new ApiResponse<>("success", userRepository.save(user));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Borrowable>>> readAll() {
        List<Borrowable> borrowables = this.repository.findAll();
        ApiResponse<List<Borrowable>> response = new ApiResponse<>("success", borrowables);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> readBook(@PathVariable int id) {
        Optional<Borrowable> bor = this.repository.findById(id);

        if (bor.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No item with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Borrowable borrowable = bor.get();
        ApiResponse<Borrowable> response = new ApiResponse<>("success", repository.save(borrowable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("records")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> readBorrowRecords() {
        List<BorrowRecord> records = this.borrowRecordRepository.findAll();
        ApiResponse<List<BorrowRecord>> response = new ApiResponse<>("success", records);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<?>> readUser(@PathVariable int id) {
        Optional<User> use = this.userRepository.findById(id);
        if (use.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "No user was found matching your credentials");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        User user = use.get();
        ApiResponse<User> response = new ApiResponse<>("success", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<ApiResponse<?>> updateBook (@PathVariable int id, @RequestBody Book bookDetails) {
        Optional<Borrowable> bor = this.repository.findById(id);

        if (bor.isEmpty() || !bor.get().getType().equals("book")) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No book with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if (!bookDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified book, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Book book = (Book) bor.get();
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());
        book.setGenre(bookDetails.getGenre());

        ApiResponse<Book> response = new ApiResponse<>("success", repository.save(book));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/game/{id}")
    public ResponseEntity<ApiResponse<?>> updateGame (@PathVariable int id, @RequestBody Game gameDetails) {
        Optional<Borrowable> bor = this.repository.findById(id);

        if (bor.isEmpty() || !bor.get().getType().equals("game")) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No game with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if (!gameDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified game, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Game game = (Game) bor.get();
        game.setTitle(gameDetails.getTitle());
        game.setPublisher(gameDetails.getPublisher());
        game.setGenre(gameDetails.getGenre());
        game.setReleaseYear(gameDetails.getReleaseYear());

        ApiResponse<Game> response = new ApiResponse<>("success", repository.save(game));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> delete (@PathVariable int id) {
        Optional<Borrowable> bor = this.repository.findById(id);

        if (bor.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No item with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Borrowable borrowable = bor.get();
        repository.delete(borrowable);
        ApiResponse<Borrowable> response = new ApiResponse<>("success", borrowable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser (@PathVariable int id) {
        Optional<User> use = this.userRepository.findById(id);

        if (use.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No user with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        User user = use.get();
        userRepository.delete(user);
        ApiResponse<User> response = new ApiResponse<>("success", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
