package com.booleanuk.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("author")
    private List<Book> books;

    @Column
    private String firstName;

    @Column
    private String lastName;

    public void addBook(Book b){
        this.books.add(b);
    }

}
