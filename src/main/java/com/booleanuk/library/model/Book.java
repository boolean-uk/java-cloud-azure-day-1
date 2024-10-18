package com.booleanuk.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties("books")
    private Author author;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String genre;

    @Column
    private int rating;

    public Book(Author author, String title, String description, String genre, int rating) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
    }

    public Book(int id, String title, String description, String genre, int rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
    }
}
