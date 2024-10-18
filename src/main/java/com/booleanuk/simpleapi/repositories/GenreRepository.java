package com.booleanuk.simpleapi.repositories;

import com.booleanuk.simpleapi.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
