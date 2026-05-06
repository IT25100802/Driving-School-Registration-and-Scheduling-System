package com.drivingschool.vehicle.entity;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private String assignedCategory;
    private String status;
    private String lastServiceDate;
}

