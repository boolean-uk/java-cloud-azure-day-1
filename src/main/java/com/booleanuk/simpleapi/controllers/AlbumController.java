package com.booleanuk.simpleapi.controllers;

import com.booleanuk.simpleapi.models.Album;
import com.booleanuk.simpleapi.models.Artist;
import com.booleanuk.simpleapi.models.Label;
import com.booleanuk.simpleapi.repositories.AlbumRepository;
import com.booleanuk.simpleapi.repositories.ArtistRepository;
import com.booleanuk.simpleapi.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController extends GenericController<Album, Integer> {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    @PostMapping
    public ResponseEntity<Album> create(@RequestBody Album album) {
        Label label = labelRepository.findById(album.getLabel().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No labels with that id were found"));

        List<Artist> artists = artistRepository.findAllById(
                album.getArtists().stream().map(Artist::getId).toList());

        if (artists.size() != album.getArtists().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more artists with the given ids were not found");
        }

        album.setLabel(label);
        album.setArtists(artists);

        Album newAlbum = albumRepository.save(album);
        return new ResponseEntity<>(newAlbum, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Album> update(@PathVariable Integer id, @RequestBody Album album) {
        Album existingAlbum = albumRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No albums with that id were found"));

        existingAlbum.setTitle(album.getTitle());
        existingAlbum.setGenre(album.getGenre());
        existingAlbum.setLabel(labelRepository.findById(album.getLabel().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No labels with that id were found")));

        List<Artist> artists = artistRepository.findAllById(
                album.getArtists().stream().map(Artist::getId).toList());

        if (artists.size() != album.getArtists().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more artists with the given ids were not found");
        }

        existingAlbum.setArtists(artists);

        try {
            Album updatedAlbum = albumRepository.save(existingAlbum);
            return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the album, please check all required fields are correct.");
        }
    }
}