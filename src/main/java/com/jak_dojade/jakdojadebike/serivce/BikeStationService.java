package com.jak_dojade.jakdojadebike.serivce;

import com.jak_dojade.jakdojadebike.dto.StationDto;
import com.jak_dojade.jakdojadebike.dto.StationInfoDto;
import com.jak_dojade.jakdojadebike.dto.StationsInfoDto;
import com.jak_dojade.jakdojadebike.exceptions.RentException;
import com.jak_dojade.jakdojadebike.model.Bike;
import com.jak_dojade.jakdojadebike.model.Station;
import com.jak_dojade.jakdojadebike.model.StationPosition;
import com.jak_dojade.jakdojadebike.repository.BikeStationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BikeStationService {

    private final BikeService bikeService;
    private final StationPositionService stationPositionService;
    private final BikeStationsRepository bikeStationsRepository;

    public StationsInfoDto getStationsInfo() {
        List<Station> stationsList = bikeStationsRepository.findAll();
        StationsInfoDto stationsInfoDto = new StationsInfoDto();
        stationsList.forEach(e -> {
            StationInfoDto infoDto = StationInfoDto.builder()
                    .stationId(e.getId())
                    .stationName(e.getName())
                    .bikesAmount(e.getBikes().size())
                    .freePositions(e.getFreePositions())
                    .occupiedPositions(e.getOccupiedPositions())
                    .build();
            stationsInfoDto.addStationInfo(infoDto);
        });
        return stationsInfoDto;
    }

    public void returnBikeToStation(String stationName, Integer bikeId, Integer positionId) {
        Bike returnedBike = bikeService.findById(bikeId);
        Station bikeStation = getStationByName(stationName);
        if (positionId != null) {
            if (bikeStation.isPositionNotBusy(positionId)) {
                StationPosition stationPosition = bikeStation.getPosition(positionId).get();

                returnedBike.setStation(bikeStation);
                returnedBike = bikeService.save(returnedBike);

                stationPosition.setBikeInPosition(returnedBike);
                bikeStation.getBikes().add(returnedBike);
                stationPositionService.save(stationPosition);
            } else {
                throw new RentException("Position " + positionId + " already occupied");
            }
        } else {
            returnedBike.setStation(bikeStation);
            bikeService.save(returnedBike);
        }
    }

    private Station getStationByName(String stationName) {
        return bikeStationsRepository.findByName(stationName)
                .orElseThrow(() -> new NoSuchElementException("Cannot find bike station with name: " + stationName));
    }

    public void rentBikeFromStation(String stationName, Integer bikeId) {
        Bike returnedBike = bikeService.findByBikeIdAndStationName(bikeId, stationName);

        if (returnedBike.getStationPosition() != null) {
            StationPosition stationPosition = returnedBike.getStationPosition();
            stationPosition.takeOutBikeFromPosition();
            returnedBike.setStationPosition(null);
            stationPositionService.save(stationPosition);
        }

        returnedBike.setStation(null);
        bikeService.save(returnedBike);
    }

    //INFO: do bardziej skomplikowanych mapowań użyłbym mapstructa tutaj mamy proste obiekty
    public StationDto createNewStation(StationDto stationDto) {
        Station station = new Station();
        station.setName(stationDto.getStationName());

        stationDto.getStationPositionDtoList().forEach(e -> {
            StationPosition stationPosition = new StationPosition();
            stationPosition.setPositionName(e.getPositionName());
            stationPosition.setStation(station);
            station.addStationPosition(stationPosition);
        });

        return toDto(bikeStationsRepository.save(station));
    }

    public static StationDto toDto(Station station) {
        return StationDto.builder()
                .id(station.getId())
                .stationName(station.getName())
                .stationPositionDtoList(station.getStationPositions()
                        .stream().map(StationPositionService::toDto)
                        .collect(Collectors.toList()))
                .build();

    }


    public void deleteStation(Integer stationId) throws NoSuchElementException {
        Station stationToDelete = bikeStationsRepository.findById(stationId)
                .orElseThrow(() -> new NoSuchElementException("Cannot find bike station with id: " + stationId));
        bikeStationsRepository.delete(stationToDelete);
    }

    public StationDto getStationById(Integer stationId) throws NoSuchElementException {
        Station station = bikeStationsRepository.findById(stationId)
                .orElseThrow(() -> new NoSuchElementException("Cannot find bike station with id: " + stationId));
        return toDto(station);
    }

    public StationDto updateStation(StationDto requestBody) {
        Station station = bikeStationsRepository.findById(requestBody.getId())
                .orElseThrow(() -> new NoSuchElementException("Cannot find bike station with id: " + requestBody.getId()));

        station.setName(requestBody.getStationName());
        requestBody.getStationPositionDtoList().forEach(e -> {
            station.getStationPositions()
                    .stream().filter(sp -> e.getId() != null && e.getId() == sp.getId()).findFirst().ifPresentOrElse(el -> {
                el.setPositionName(e.getPositionName());
            }, () -> {
                StationPosition stationPosition = new StationPosition();
                stationPosition.setPositionName(e.getPositionName());
                stationPosition.setStation(station);
                station.getStationPositions().add(stationPosition);
            });
        });

        return toDto(bikeStationsRepository.save(station));
    }

    /**
     * Do usunięcia metoda testowa do generowania naprostrzego zbioru stacji rowerów i stanowisk
     */
    public void createTestSet() {
        Station station = new Station();
        station.setName("Stacja 1");

        station = bikeStationsRepository.save(station);

        Bike bike = new Bike();
        bike.setOperator("Operator 2");
        bike.setStation(station);

        Bike bike2 = new Bike();
        bike2.setStation(station);
        bike.setOperator("Operator 1");

        bike = bikeService.save(bike);
        bike2 = bikeService.save(bike2);


        StationPosition stationPosition = new StationPosition();
        stationPosition.setStation(station);
        stationPosition.setBusy(true);
        stationPosition.setBike(bike);
        stationPosition.setPositionName("Position name");

        stationPositionService.save(stationPosition);
    }

}
