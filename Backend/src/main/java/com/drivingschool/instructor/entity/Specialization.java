package com.drivingschool.instructor.entity;

import lombok.Getter;

@Getter
public enum Specialization {
    MOTORCYCLE("Motorcycle"),
    
    HEAVY_VEHICLE("Heavy Vehicle"),
    

    private final String label;

    Specialization(String label) {
        this.label = label;
    }
}
