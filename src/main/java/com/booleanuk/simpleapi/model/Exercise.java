package com.booleanuk.simpleapi.model;

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
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int sets;
    private int reps;
    private int weightInKg;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
}
