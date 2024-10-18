package com.booleanuk.simpleapi.repositories;

import com.booleanuk.simpleapi.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
