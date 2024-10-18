package com.booleanuk.simpleapi.repositories;

import com.booleanuk.simpleapi.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
