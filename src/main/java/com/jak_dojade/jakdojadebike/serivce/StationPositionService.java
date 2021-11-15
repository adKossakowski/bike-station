package com.jak_dojade.jakdojadebike.serivce;

import com.jak_dojade.jakdojadebike.dto.StationPositionDto;
import com.jak_dojade.jakdojadebike.model.StationPosition;
import com.jak_dojade.jakdojadebike.repository.PositionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StationPositionService {

    private final PositionsRepository positionsRepository;

    public StationPosition save(StationPosition stationPosition){
        return positionsRepository.save(stationPosition);
    }

    public static StationPositionDto toDto(StationPosition stationPosition){
        return StationPositionDto.builder()
                .id(stationPosition.getId())
                .positionName(stationPosition.getPositionName())
                .busy(stationPosition.isBusy())
                .build();
    }
}
