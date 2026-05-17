package com.drivingschool.instructor.dto;

import com.drivingschool.instructor.entity.Specialization;
import lombok.Data;

@Data
public class InstructorDTO {
    private String id;
    
    
    private String phone;
    private String nic;
    private String licenseNumber;
    private Specialization specialization;
    
    private String status;
    private String joinedDate;
    private String username;
    private String password;
    
}
