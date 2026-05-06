package com.drivingschool.payment.entity;

import com.drivingschool.student.entity.LicenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "driving_package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private double price;
    private String duration;
    @Enumerated(EnumType.STRING)
    private LicenseCategory licenseCategory;
}
