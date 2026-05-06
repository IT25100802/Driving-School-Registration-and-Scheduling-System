package com.drivingschool.payment.dto;

import com.drivingschool.payment.entity.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDTO {
    private String id;
    private String studentId;
    private String studentName;
    private String packageId;
    private String packageName;
    private double amount;
    private String paymentDate;
    private String paymentMethod;
    private PaymentStatus status;
    private String notes;
}
