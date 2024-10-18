package com.booleanuk.simpleapi.Repositories;

import com.booleanuk.simpleapi.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
