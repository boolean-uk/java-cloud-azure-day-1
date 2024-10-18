package com.booleanuk.simpleapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties("room")
    private List<Penguin> penguins;

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties("room")
    private List<Game> games;

    @Column
    private String name;

    @Column
    private int capacity;

    @Column
    private String theme;

    public Room(int id) {
        this.id = id;
    }

    public Room(List<Penguin> penguins, String name, int capacity, String theme) {
        this.penguins = penguins;
        this.name = name;
        this.capacity = capacity;
        this.theme = theme;
    }
}
