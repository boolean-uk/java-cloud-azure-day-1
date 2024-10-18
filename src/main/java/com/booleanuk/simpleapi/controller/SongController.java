package com.booleanuk.simpleapi.controller;


import com.booleanuk.simpleapi.dto.SongDTO;
import com.booleanuk.simpleapi.model.Album;
import com.booleanuk.simpleapi.model.Artist;
import com.booleanuk.simpleapi.model.Genre;
import com.booleanuk.simpleapi.model.Song;
import com.booleanuk.simpleapi.repository.AlbumRepository;
import com.booleanuk.simpleapi.repository.ArtistRepository;
import com.booleanuk.simpleapi.repository.GenreRepository;
import com.booleanuk.simpleapi.repository.SongRepository;
import com.booleanuk.simpleapi.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("songs")
public class SongController {
    @Autowired
    SongRepository repository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @GetMapping
    public ResponseEntity<SongListResponse> getAll() {
        List<Song> songs = ResponseEntity.ok(this.repository.findAll()).getBody();
        SongListResponse songListResponse = new SongListResponse();
        songListResponse.set(songs);
        return ResponseEntity.ok(songListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable Integer id) {
        Song song = this.repository.findById(id).orElse(null);
        if(song == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Song not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(response(song));
    }

    @PostMapping()
    public ResponseEntity<Response<?>> create(@RequestBody SongDTO songDTO) {
        Set<Genre> genres = new HashSet<>();
        Artist artist = this.artistRepository.findById(songDTO.getArtistId()).orElse(null);
        Album album = this.albumRepository.findById(songDTO.getAlbumId()).orElse(null);
        ErrorResponse errorResponse = new ErrorResponse();

        if(artist == null || album == null) {
            errorResponse.set("Artist/album is not found for the song");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if(songDTO.getGenreIds() != null) {
            songDTO.getGenreIds().forEach(e -> this.genreRepository.findById(e).ifPresent(genres::add));
        }else {
            errorResponse.set("Genres is empty in song");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }


        Song song = new Song(
                songDTO.getSongName(),
                songDTO.getDuration(),
                genres,
                artist,
                album
        );

        this.repository.save(song);
        album.addSong(song);
        this.albumRepository.save(album);
        return ResponseEntity.ok(response(song));
    }

    public SongResponse response(Song song) {
        SongResponse songResponse = new SongResponse();
        songResponse.set(song);
        return songResponse;
    }
}
