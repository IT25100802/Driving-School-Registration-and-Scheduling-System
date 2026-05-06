package com.drivingschool.common.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullName;
    private String email;
    private String phone;
}


