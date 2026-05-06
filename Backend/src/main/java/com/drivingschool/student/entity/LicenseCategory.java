package com.drivingschool.student.entity;

import lombok.Getter;

@Getter
public enum LicenseCategory {
    A("Motorcycle"),
    B("Light Vehicle"),
    C("Heavy Vehicle"),
    D("Bus");

    private final String label;

    LicenseCategory(String label) {
        this.label = label;
    }
}
