package com.booleanuk.simpleapi.Repositories;

import com.booleanuk.simpleapi.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
