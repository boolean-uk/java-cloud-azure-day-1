package com.booleanuk.simpleapi.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "albumName")
    private String albumName;

    @OneToMany(mappedBy = "album")
    private List<Song> songList;

    @ManyToOne()
    @JoinColumn(name = "artist_id", nullable = false)
    @JsonIncludeProperties({"id", "artistName"})
    private Artist artist;


    public Album(String albumName, List<Song> songList, Artist artist){
        this.albumName = albumName;
        this.songList = songList;
        this.artist = artist;
    }


    public void addSong(Song song) {
        this.songList.add(song);
    }
}
