package com.booleanuk.simpleapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private int year;

    @Column
    private String genre;

    @Column
    private int numberOfTracks;

    @ManyToMany
    @JoinTable(
            name = "album_artist",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    @JsonIgnoreProperties("albums")
    private List<Artist> artists;

    @ManyToOne
    @JoinColumn(name = "label_id", nullable = false)
    @JsonIncludeProperties({"name", "location"})
    private Label label;

    public Album(String title, int year, String genre, int numberOfTracks) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.numberOfTracks = numberOfTracks;
    }

    public Album(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Albums{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", numberOfTracks=" + numberOfTracks +
                '}';
    }
}
