package com.booleanuk.simpleapi.repositories;

import com.booleanuk.simpleapi.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
