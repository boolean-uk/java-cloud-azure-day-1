package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRepository extends JpaRepository<Videogame, Integer> {
}
