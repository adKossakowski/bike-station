package com.jak_dojade.jakdojadebike.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "stations")
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;


    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY)
    private List<Bike> bikes;

    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StationPosition> stationPositions;

    public void addStationPosition(StationPosition stationPosition){
        if(stationPositions != null){
            stationPositions.add(stationPosition);
        }else{
            stationPositions = new ArrayList<>(Collections.singletonList(stationPosition));
        }
    }

    public int getFreePositions() {
        return (int) stationPositions.stream().filter(e -> !e.isBusy()).count();
    }

    public int getOccupiedPositions() {
        return (int) stationPositions.stream().filter(StationPosition::isBusy).count();
    }

    public boolean isPositionNotBusy(Integer positionId) {
        Optional<StationPosition> position = stationPositions.stream().filter(e -> positionId == e.getId()).findAny();
        return position.isPresent() && !position.get().isBusy();
    }

    public boolean isPositionBusy(Integer positionId) {
        Optional<StationPosition> position = stationPositions.stream().filter(e -> positionId == e.getId()).findAny();
        return position.isPresent() && position.get().isBusy();
    }

    public Optional<StationPosition> getPosition(Integer positionId){
        return stationPositions.stream().filter(e -> positionId == e.getId()).findFirst();
    }
}
