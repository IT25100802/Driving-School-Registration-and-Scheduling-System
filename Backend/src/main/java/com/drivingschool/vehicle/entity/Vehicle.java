package com.drivingschool.vehicle.entity;

import com.drivingschool.payment.entity.LicenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {
    @Id
    private String id;

    private String registrationNumber;
    private String make;
    private String model;
    private int year;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private LicenseCategory assignedCategory;
    
    private String status;
}

