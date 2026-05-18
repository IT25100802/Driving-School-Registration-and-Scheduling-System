package com.drivingschool.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CoursePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    @jakarta.persistence.Lob
    @jakarta.persistence.Column(columnDefinition = "LONGTEXT")
    private String description;
    private double price;
    private int theoryHours;
    private int practicalHours;

    @Enumerated(EnumType.STRING)
    private LicenseCategory licenseCategory;
}
