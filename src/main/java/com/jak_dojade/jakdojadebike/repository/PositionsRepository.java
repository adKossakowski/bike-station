package com.jak_dojade.jakdojadebike.repository;

import com.jak_dojade.jakdojadebike.model.StationPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionsRepository extends JpaRepository<StationPosition, Integer> {
}
