package com.booleanuk.simpleapi.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String birth;

    @Column
    private String country;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author(String name, String birth, String country) {
        this.name = name;
        this.birth = birth;
        this.country = country;
    }
}
