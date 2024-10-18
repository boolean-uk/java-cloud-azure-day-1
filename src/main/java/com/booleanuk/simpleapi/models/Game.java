package com.booleanuk.simpleapi.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String gameTitle;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIncludeProperties(value = {"studioName", "gameTitle"})
    private Studio studio;

    public Game(int id){this.id = id;}
}
