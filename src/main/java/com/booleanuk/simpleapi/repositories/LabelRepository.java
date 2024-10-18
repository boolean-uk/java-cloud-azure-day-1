package com.booleanuk.simpleapi.repositories;

import com.booleanuk.simpleapi.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Integer> {
}
