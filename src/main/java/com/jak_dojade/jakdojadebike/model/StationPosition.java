package com.jak_dojade.jakdojadebike.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "station_positions")
@NoArgsConstructor
@AllArgsConstructor
public class StationPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String positionName;

    private boolean busy;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bike_id", referencedColumnName = "id")
    private Bike bike;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", updatable = false)
    private Station station;

    public void setBikeInPosition(Bike bike) {
        this.busy = true;
        this.bike = bike;
    }

    public void takeOutBikeFromPosition() {
        this.busy = false;
        this.bike = null;
    }
}
