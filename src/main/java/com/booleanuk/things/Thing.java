package com.booleanuk.things;

import com.booleanuk.producers.Producer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "things")
public class Thing {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private int id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal price;

  @ManyToOne
  @JsonIgnoreProperties(value = "things")
  @JoinColumn(name = "producer_id", nullable = false)
  private Producer producer;
}