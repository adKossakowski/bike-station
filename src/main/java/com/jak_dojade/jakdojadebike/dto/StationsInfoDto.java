package com.jak_dojade.jakdojadebike.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StationsInfoDto {

    private List<StationInfoDto> stationsInfoList = new ArrayList<>();

    public void addStationInfo(StationInfoDto stationDto) {
        stationsInfoList.add(stationDto);
    }
}
