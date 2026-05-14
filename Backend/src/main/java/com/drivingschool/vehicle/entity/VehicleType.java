package com.drivingschool.vehicle.entity;

import lombok.Getter;
//Veicle types add 

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
