package com.jak_dojade.jakdojadebike.repository;

import com.jak_dojade.jakdojadebike.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BikeStationsRepository extends JpaRepository<Station, Integer> {

    Optional<Station> findByName(String stationName);
}
