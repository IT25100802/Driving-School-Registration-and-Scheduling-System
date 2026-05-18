package com.drivingschool.payment.entity;

import lombok.Getter;

@Getter
public enum LicenseCategory {
    MOTORCYCLE("Motorcycle"),
    LIGHT_VEHICLE("Light Vehicle"),
    HEAVY_VEHICLE("Heavy Vehicle"),
    BUS("Bus"),
    B1("B1 Category");

    private final String label;

    LicenseCategory(String label) {
        this.label = label;
    }
}
