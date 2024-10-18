package com.booleanuk.simpleapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "artistName")
    private String artistName;

    @Column(name = "dob")
    private String dob;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;

    public Artist(String artistName, String dob) {
        this.artistName = artistName;
        this.dob = dob;
    }
}
