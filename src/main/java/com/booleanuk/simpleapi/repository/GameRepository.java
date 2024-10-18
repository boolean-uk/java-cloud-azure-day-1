package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

}
