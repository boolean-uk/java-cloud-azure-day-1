package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {}
