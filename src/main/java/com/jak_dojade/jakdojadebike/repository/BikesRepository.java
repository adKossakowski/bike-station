package com.jak_dojade.jakdojadebike.repository;

import com.jak_dojade.jakdojadebike.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BikesRepository extends JpaRepository<Bike, Integer> {

    @Query("SELECT b from Bike b join Station s on s.id = b.station where b.id = :bikeId and s.name = :stationName")
    Optional<Bike> findByIdAndStationName(@Param("bikeId") Integer bikeId, @Param("stationName") String stationName);
}
