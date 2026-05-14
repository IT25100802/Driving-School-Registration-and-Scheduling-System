package com.drivingschool.admin.dto;

import com.drivingschool.admin.entity.AdminRole;
import lombok.Data;

@Data
public class AdminDTO {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private AdminRole role;
    private String password;
}
