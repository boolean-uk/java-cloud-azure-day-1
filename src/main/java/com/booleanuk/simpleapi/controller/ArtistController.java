package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.dto.ArtistDTO;
import com.booleanuk.simpleapi.model.Artist;
import com.booleanuk.simpleapi.repository.ArtistRepository;
import com.booleanuk.simpleapi.responses.ArtistListResponse;
import com.booleanuk.simpleapi.responses.ArtistResponse;
import com.booleanuk.simpleapi.responses.ErrorResponse;
import com.booleanuk.simpleapi.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("artists")
@CrossOrigin(origins = "http://localhost:5173")
public class ArtistController {

    @Autowired
    ArtistRepository repository;

    @GetMapping
    public ResponseEntity<ArtistListResponse> getAll() {
        List<Artist> artists = ResponseEntity.ok(this.repository.findAll()).getBody();
        ArtistListResponse artistListResponse = new ArtistListResponse();
        artistListResponse.set(artists);
        return ResponseEntity.ok(artistListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable Integer id) {
        Artist artist = this.repository.findById(id).orElse(null);
        if(artist == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Artist not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return  ResponseEntity.ok(response(artist));
    }

    @PostMapping()
    public ResponseEntity<Response<?>> create(@RequestBody ArtistDTO artistDTO) {
        Artist artist = new Artist(
                artistDTO.getArtistName(),
                artistDTO.getDob()
        );

        this.repository.save(artist);

        return ResponseEntity.ok(response(artist));
    }


    public ArtistResponse response(Artist artist) {
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.set(artist);
        return artistResponse;
    }

}
