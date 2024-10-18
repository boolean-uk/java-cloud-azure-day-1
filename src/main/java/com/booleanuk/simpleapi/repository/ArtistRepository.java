package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
