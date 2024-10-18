package com.booleanuk.simpleapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "penguins")
public class Penguin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "penguin_id")
    @JsonIncludeProperties(value = {"username", "meetable", "visitor", "villainous"})
    private Room room;

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
