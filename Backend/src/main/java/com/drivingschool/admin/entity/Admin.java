package com.drivingschool.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {
    @Id
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private AdminRole role;
}
