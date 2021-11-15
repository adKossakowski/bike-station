package com.jak_dojade.jakdojadebike.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationInfoDto {

    private String stationName;
    private int stationId;
    private int freePositions;
    private int occupiedPositions;
    private int bikesAmount;
}
