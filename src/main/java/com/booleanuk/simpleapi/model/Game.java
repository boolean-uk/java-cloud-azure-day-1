package com.booleanuk.simpleapi.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String title;


    private String genre;


    private String publisher;


    private String developer;


    private Integer releaseYear;


    public Game(String title, String genre, String publisher, String developer, Integer releaseYear) {

        this.title=title;
        this.genre=genre;
        this.publisher=publisher;
        this.developer=developer;
        this.releaseYear=releaseYear;
    }

}
