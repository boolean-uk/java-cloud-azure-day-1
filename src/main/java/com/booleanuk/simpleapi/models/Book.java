package com.booleanuk.simpleapi.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "genre.id", nullable = false)
    @JsonIgnore
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "author.id", nullable = false)
    @JsonIgnore
    private Author author;

    @Column
    private int publicationDate;

    public Book(String title, Genre genre, Author author, int publicationDate) {
        this.author = author;
        this.genre = genre;
        this.title = title;
        this.publicationDate = publicationDate;
    }

}
