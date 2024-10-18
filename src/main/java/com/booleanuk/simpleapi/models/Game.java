package com.booleanuk.simpleapi.models;

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
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIncludeProperties(value = {"name", "duration"})
    private Room room;

    @Column
    private String name;

    @Column
    private int maxCapacity;

    @Column
    private int minCapacity;

    @Column
    private int duration;

    public Game(int id) {
        this.id = id;
    }

    public Game(Room room, String name, int maxCapacity, int minCapacity, int duration) {
        this.room = room;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.duration = duration;
    }
}
