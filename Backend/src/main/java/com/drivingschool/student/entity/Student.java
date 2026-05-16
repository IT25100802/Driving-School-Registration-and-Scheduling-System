package com.drivingschool.student.entity;

import com.drivingschool.common.entity.Person;
import com.drivingschool.payment.entity.LicenseCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends Person {
    private String nic;
    private String dateOfBirth;
    private String address;
    @Enumerated(EnumType.STRING)
    private LicenseCategory licenseCategory;
    private String enrolledDate;
    private String status;

    @Enumerated(EnumType.STRING)
    private TrainingPhase trainingPhase;
    private String packageId;
}

