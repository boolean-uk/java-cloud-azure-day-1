package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
