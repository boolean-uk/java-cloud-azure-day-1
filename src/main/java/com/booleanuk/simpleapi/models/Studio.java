package com.booleanuk.simpleapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "Studios")
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String studioName;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("studio")
    private List<Game> games;

    public Studio(int id){this.id = id;}
}