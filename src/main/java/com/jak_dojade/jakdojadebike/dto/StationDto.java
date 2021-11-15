package com.jak_dojade.jakdojadebike.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationDto {

    private Integer id;
    @NotEmpty
    private String stationName;
    List<StationPositionDto> stationPositionDtoList;
}
