package com.booleanuk.simpleapi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String location;

    @OneToMany(mappedBy = "developer")
    @JsonIgnoreProperties(value = "developer")
    private List<Videogame> videogames;


    public Developer(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Developer(Integer id) {
        this.id = id;
    }
}
