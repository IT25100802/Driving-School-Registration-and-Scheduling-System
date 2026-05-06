package com.drivingschool.instructor.entity;

import lombok.Getter;

@Getter
public enum Specialization {
    A("Motorcycle"),
    B("Light Vehicle"),
    C("Heavy Vehicle"),
    D("Bus");

    private final String label;

    Specialization(String label) {
        this.label = label;
    }
}
