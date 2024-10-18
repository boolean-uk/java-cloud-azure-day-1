package com.booleanuk.simpleapi.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "songName")
    private String songName;

    //Duration is in seconds
    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @JsonIncludeProperties({"id", "artistName"})
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    @JsonIncludeProperties({"id", "albumName"})
    private Album album;

    @ManyToMany
    @JoinTable(
            name = "song_genres",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonIncludeProperties({"id", "genre"})
    private Set<Genre> genres;


    public Song(String songName, Integer duration, Set<Genre> genres, Artist artist, Album album) {
        this.songName = songName;
        this.duration = duration;
        this.genres = genres;
        this.artist = artist;
        this.album = album;
    }

}
