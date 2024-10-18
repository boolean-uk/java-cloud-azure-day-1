package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
