package com.booleanuk.simpleapi.models;

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
@Table(name = "penguins")
public class Penguin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;

    @Column
    private String firstAppearance;

    @Column
    private String mostRecentGiveaway;

    @Column
    private Boolean meetable;

    @Column
    private Boolean visitor;

    @Column
    private Boolean villainous;

    public Penguin(int id) {
        this.id = id;
    }

    public Penguin(String username, String firstAppearance, String mostRecentGiveaway, Boolean meetable, Boolean visitor, Boolean villainous) {
        this.username = username;
        this.firstAppearance = firstAppearance;
        this.mostRecentGiveaway = mostRecentGiveaway;
        this.meetable = meetable;
        this.visitor = visitor;
        this.villainous = villainous;
    }
}
