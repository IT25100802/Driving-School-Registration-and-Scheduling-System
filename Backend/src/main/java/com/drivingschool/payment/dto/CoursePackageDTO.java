package com.drivingschool.payment.dto;

import com.drivingschool.payment.entity.LicenseCategory;
import lombok.Data;

@Data
public class CoursePackageDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private int theoryHours;
    private int practicalHours;
    private LicenseCategory licenseCategory;
}
