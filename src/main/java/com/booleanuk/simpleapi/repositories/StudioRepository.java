package com.booleanuk.simpleapi.repositories;

import com.booleanuk.simpleapi.models.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<Studio, Integer> {
}
