package com.booleanuk.simpleapi.Repositories;

import com.booleanuk.simpleapi.models.Penguin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenguinRepository extends JpaRepository<Penguin, Integer> {
    public List<Penguin> findAllByVillainousTrue();

    public List<Penguin> findAllByVisitorTrue();

    public List<Penguin> findAllByMeetableTrue();

}
