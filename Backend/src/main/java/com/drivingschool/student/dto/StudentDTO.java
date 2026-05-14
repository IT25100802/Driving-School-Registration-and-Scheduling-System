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
   
}
