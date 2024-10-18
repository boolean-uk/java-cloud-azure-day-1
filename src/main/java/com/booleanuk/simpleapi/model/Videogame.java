package com.booleanuk.simpleapi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "videogames")
public class Videogame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String rating;

    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "developer_id", nullable = false)
    @JsonIgnoreProperties(value = "videogames")
    private Developer developer;


    public Videogame(String title, String rating, Integer releaseYear) {
        this.title = title;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public Videogame(Integer id) {
        this.id = id;
    }
}
