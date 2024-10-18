package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
}
