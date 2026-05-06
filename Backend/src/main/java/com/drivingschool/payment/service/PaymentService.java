package com.drivingschool.payment.service;

import com.drivingschool.payment.dto.PaymentDTO;
import com.drivingschool.payment.entity.Package;
import com.drivingschool.payment.entity.Payment;
import com.drivingschool.payment.entity.PaymentStatus;
import com.drivingschool.payment.repository.PackageRepository;
import com.drivingschool.payment.repository.PaymentRepository;
import com.drivingschool.student.entity.Student;
import com.drivingschool.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(String id) {
        return paymentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = convertToEntity(paymentDTO);
        if (payment.getPaymentDate() == null || payment.getPaymentDate().isEmpty()) {
            payment.setPaymentDate(LocalDate.now().toString());
        }
        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.PAID);
        }
        return convertToDTO(paymentRepository.save(payment));
    }

    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }

    public PaymentDTO refundPayment(String id) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment.setStatus(PaymentStatus.REFUNDED);
                    return convertToDTO(paymentRepository.save(payment));
                })
                .orElse(null);
    }

    private PaymentDTO convertToDTO(Payment p) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(p.getId());
        dto.setStudentId(p.getStudentId());
        dto.setPackageId(p.getPackageId());
        dto.setAmount(p.getAmount());
        dto.setPaymentDate(p.getPaymentDate());
        dto.setPaymentMethod(p.getPaymentMethod());
        dto.setStatus(p.getStatus());
        dto.setNotes(p.getNotes());

        studentRepository.findById(p.getStudentId())
                .ifPresent(student -> dto.setStudentName(student.getFullName()));

        packageRepository.findById(p.getPackageId())
                .ifPresent(pkg -> dto.setPackageName(pkg.getName()));

        return dto;
    }


    private Payment convertToEntity(PaymentDTO dto) {
        Payment p = new Payment();
        p.setId(dto.getId());
        p.setStudentId(dto.getStudentId());
        p.setPackageId(dto.getPackageId());
        p.setAmount(dto.getAmount());
        p.setPaymentDate(dto.getPaymentDate());
        p.setPaymentMethod(dto.getPaymentMethod());
        p.setStatus(dto.getStatus());
        p.setNotes(dto.getNotes());
        return p;
    }
}
