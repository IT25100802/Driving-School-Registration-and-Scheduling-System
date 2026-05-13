package com.drivingschool.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String studentId;
    private String packageId;
    private double amount;
    private String paymentDate;
    private String paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String notes;
}