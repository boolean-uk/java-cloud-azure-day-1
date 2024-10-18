package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Integer> {
}
