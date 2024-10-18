package com.booleanuk.simpleapi.controllers;

import com.booleanuk.simpleapi.models.Game;
import com.booleanuk.simpleapi.models.Studio;
import com.booleanuk.simpleapi.repositories.GameRepository;
import com.booleanuk.simpleapi.repositories.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("game")
public class GameController {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private StudioRepository studioRepository;

    @PostMapping("studio/{id}")
    public ResponseEntity<Game> create(@PathVariable int id, @RequestBody Game game){
        Studio studio = this.studioRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No studio with that id")
        );
        game.setStudio(studio);
        this.gameRepository.save(game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Game> getAll(){
        return this.gameRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Game> getOne(@PathVariable int id){
        Game game = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that id")
        );
        return ResponseEntity.ok(game);
    }

    @PutMapping("{id}")
    public ResponseEntity<Game> update(@PathVariable int id, @RequestBody Game game){
        Game gameToUpdate = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that id")
        );
        gameToUpdate.setGameTitle(game.getGameTitle());
        return new ResponseEntity<>(this.gameRepository.save(gameToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Game> delete(@PathVariable int id){
        Game game = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that id")
        );
        this.gameRepository.delete(game);
        return ResponseEntity.ok(game);
    }
}
