package com.booleanuk.simpleapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String workoutType;
    private LocalDateTime workoutDate;

    @Column(columnDefinition = "jsonb")
    private String exercises;

    public Workout(String workoutType, String exercises) {
        this.workoutType = workoutType;
        this.exercises = exercises;
    }

    @PrePersist
    public void onCreate() {
        if (this.workoutDate == null) {
            this.workoutDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.workoutDate = LocalDateTime.now();
    }
}
