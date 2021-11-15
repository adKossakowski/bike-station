package com.jak_dojade.jakdojadebike.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationPositionDto {

    private Integer id;
    private String positionName;
    private boolean busy;
}
