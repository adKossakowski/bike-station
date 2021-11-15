package com.jak_dojade.jakdojadebike.controller;


import com.jak_dojade.jakdojadebike.serivce.BikeStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/support")
public class SupportController {

    private final BikeStationService bikeStationService;

    @PostMapping("/create-test-set")
    void createTestSet() {
        bikeStationService.createTestSet();
    }


}
