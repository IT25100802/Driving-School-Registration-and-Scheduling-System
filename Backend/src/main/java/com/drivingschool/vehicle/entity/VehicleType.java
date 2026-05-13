package com.drivingschool.vehicle.entity;

import lombok.Getter;

@Getter
public enum VehicleType {
    CAR("Car"),
    MOTORCYCLE("Motorcycle"),
    VAN("Van"),
    BUS("Bus"),
    LORRY("Lorry"),
    

    private final String label;

    VehicleType(String label) {
        this.label = label;
    }
}
