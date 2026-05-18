package com.drivingschool.instructor.entity;

import lombok.Getter;

@Getter
public enum Specialization {
    MOTORCYCLE("Motorcycle"),
    LIGHT_VEHICLE("Light Vehicle"),
    HEAVY_VEHICLE("Heavy Vehicle"),
    BUS("Bus");

    private final String label;

    Specialization(String label) {
        this.label = label;
    }
}
