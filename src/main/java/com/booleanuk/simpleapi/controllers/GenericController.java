package com.booleanuk.simpleapi.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public abstract class GenericController<T, ID> {

    @Autowired
    private JpaRepository<T, ID> repository;

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        T entity = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        try {
            T newEntity = repository.save(entity);
            return new ResponseEntity<>(newEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create entity, please check all required fields are correct.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        Optional<T> existingEntityOptional  = repository.findById(id);
        if (existingEntityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }

        T existingEntity = existingEntityOptional .get();

        BeanUtils.copyProperties(entity, existingEntity, "id");

        try {
            T updatedEntity = repository.save(existingEntity);
            return new ResponseEntity<>(updatedEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the entity, please check all required fields are correct.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable ID id) {
        T entityToDelete = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));
        repository.deleteById(id);
        return ResponseEntity.ok(entityToDelete);
    }
}
