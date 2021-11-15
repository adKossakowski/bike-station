package com.jak_dojade.jakdojadebike.controller;

import com.jak_dojade.jakdojadebike.dto.StationDto;
import com.jak_dojade.jakdojadebike.dto.StationsInfoDto;
import com.jak_dojade.jakdojadebike.serivce.BikeStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Validated
@RestController
@RequestMapping("/jak-dojade/bike-station")
@RequiredArgsConstructor
public class BikeStationController {

    private final BikeStationService bikeStationService;

    @PutMapping("/return-bike")
    ResponseEntity<Void> returnBike(@RequestParam("stationName") String stationName,
                                    @RequestParam("bikeId") Integer bikeId,
                                    @RequestParam(name = "positionId", required = false) Integer positionId) {
        bikeStationService.returnBikeToStation(stationName, bikeId, positionId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/rent-bike")
    ResponseEntity<Void> rentBike(@RequestParam("stationName") String stationName, @RequestParam("bikeId") Integer bikeId) {
        bikeStationService.rentBikeFromStation(stationName, bikeId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/create")
    ResponseEntity<StationDto> createNewStation(@Valid @RequestBody StationDto stationDto) {
        return ResponseEntity.ok(bikeStationService.createNewStation(stationDto));
    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteBikeStation(@RequestParam("stationId") Integer stationId) {
        try {
            bikeStationService.deleteStation(stationId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{stationId}")
    ResponseEntity<StationDto> getStation(@PathVariable("stationId") Integer stationId) {
        try {
            return ResponseEntity.ok(bikeStationService.getStationById(stationId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    ResponseEntity<StationDto> updateStation(@Valid @RequestBody StationDto body) {
        try {
            return ResponseEntity.ok(bikeStationService.updateStation(body));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stations-info")
    ResponseEntity<StationsInfoDto> getStationsInfo() {
        return ResponseEntity.ok(bikeStationService.getStationsInfo());
    }


}
