package com.jak_dojade.jakdojadebike.serivce;

import com.jak_dojade.jakdojadebike.model.Bike;
import com.jak_dojade.jakdojadebike.repository.BikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class BikeService {

    private final BikesRepository bikesRepository;

    public Bike save(Bike bike){
        return bikesRepository.save(bike);
    }

    public Bike findById(Integer id){
        return bikesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find bike with id: " + id));
    }

    public Bike findByBikeIdAndStationName(Integer id, String stationName){
        return bikesRepository.findByIdAndStationName(id, stationName)
                .orElseThrow(() -> new NoSuchElementException("Cannot find bike with id: " + id + " in station with name: " + stationName));
    }
}
