package com.booleanuk.simpleapi.controller;

import com.booleanuk.simpleapi.dto.AlbumDTO;
import com.booleanuk.simpleapi.model.Album;
import com.booleanuk.simpleapi.model.Artist;
import com.booleanuk.simpleapi.model.Song;
import com.booleanuk.simpleapi.repository.AlbumRepository;
import com.booleanuk.simpleapi.repository.ArtistRepository;
import com.booleanuk.simpleapi.repository.SongRepository;
import com.booleanuk.simpleapi.responses.AlbumListResponse;
import com.booleanuk.simpleapi.responses.AlbumResponse;
import com.booleanuk.simpleapi.responses.ErrorResponse;
import com.booleanuk.simpleapi.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("albums")
public class AlbumController {

    @Autowired
    AlbumRepository repository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    SongRepository songRepository;

    @GetMapping
    public ResponseEntity<AlbumListResponse> getAll() {
        List<Album> albums = ResponseEntity.ok(this.repository.findAll()).getBody();
        AlbumListResponse albumListResponse = new AlbumListResponse();
        albumListResponse.set(albums);
        return ResponseEntity.ok(albumListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable Integer id) {
        Album album = this.repository.findById(id).orElse(null);
        if(album == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Album not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(response(album));
    }

    @PostMapping()
    public ResponseEntity<Response<?>> create(@RequestBody AlbumDTO albumDTO) {
        List<Song> songList = new ArrayList<>();
        Artist artist = this.artistRepository.findById(albumDTO.getArtistId()).orElse(null);

        if(albumDTO.getSongListIds() != null) {
            albumDTO.getSongListIds().forEach(e -> {
                this.songRepository.findById(e).ifPresent(songList::add);
            });
        }
        if(artist == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Artist not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Album album = new Album(
                albumDTO.getAlbumName(),
                songList,
                artist
        );
        this.repository.save(album);
        return ResponseEntity.ok(response(album));
    }



    public AlbumResponse response(Album album) {
        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.set(album);
        return albumResponse;
    }
}
