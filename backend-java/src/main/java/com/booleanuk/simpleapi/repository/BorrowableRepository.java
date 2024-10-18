package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Borrowable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowableRepository extends JpaRepository<Borrowable, Integer> {
}
