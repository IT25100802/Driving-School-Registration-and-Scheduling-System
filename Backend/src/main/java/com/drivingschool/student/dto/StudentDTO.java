package com.drivingschool.student.dto;

import com.drivingschool.payment.entity.LicenseCategory;
import com.drivingschool.student.entity.TrainingPhase;
import lombok.Data;

@Data
public class StudentDTO {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String nic;
    private String dateOfBirth;
    private String address;
    private String licenseCategory;
    private String enrolledDate;
    private String status;
    private TrainingPhase trainingPhase;
    private String packageId;
    private String packageName;
}
