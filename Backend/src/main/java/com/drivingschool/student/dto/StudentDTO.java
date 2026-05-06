package com.drivingschool.student.dto;

import com.drivingschool.student.entity.LicenseCategory;
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
    private LicenseCategory licenseCategory;
    private String enrolledDate;
    private String status;
}
