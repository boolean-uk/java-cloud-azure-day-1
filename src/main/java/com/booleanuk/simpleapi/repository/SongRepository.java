package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {
}
