package com.drivingschool.vehicle.dto;

import com.drivingschool.vehicle.entity.VehicleType;
import lombok.Data;

@Data
public class VehicleDTO {
    private String id;
    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    private VehicleType vehicleType;
    private String assignedCategory;
    private String status;
    private String lastServiceDate;
}
