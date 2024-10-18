package com.booleanuk.simpleapi.repositories;

import com.booleanuk.simpleapi.models.ERole;
import com.booleanuk.simpleapi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}