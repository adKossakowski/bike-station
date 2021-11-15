package com.jak_dojade.jakdojadebike.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bikes")
@NoArgsConstructor
@AllArgsConstructor
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String operator;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @OneToOne(mappedBy = "bike", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StationPosition stationPosition;

}
