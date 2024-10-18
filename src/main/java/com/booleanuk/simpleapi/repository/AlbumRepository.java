package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
