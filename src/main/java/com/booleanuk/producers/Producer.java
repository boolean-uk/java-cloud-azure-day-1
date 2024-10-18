package com.booleanuk.producers;

import com.booleanuk.things.Thing;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producers")
public class Producer {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private int id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("producer")
  private List<Thing> things;

  public Producer(int id) {
    this.id = id;
  }
}