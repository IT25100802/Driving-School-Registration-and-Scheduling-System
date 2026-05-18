package com.drivingschool.payment.service;

import com.drivingschool.payment.dto.PaymentDTO;
import com.drivingschool.payment.entity.Payment;
import com.drivingschool.payment.entity.PaymentMethod;
import com.drivingschool.payment.entity.PaymentStatus;
import com.drivingschool.payment.repository.PaymentRepository;
import com.drivingschool.payment.repository.CoursePackageRepository;
import com.drivingschool.student.entity.Student;
import com.drivingschool.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CoursePackageRepository coursePackageRepository;

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
        if (payment.getPaymentDate() == null || payment.getPaymentDate().trim().isEmpty()) {
            payment.setPaymentDate(LocalDate.now().toString());
        }

        // Logic: Bank Transfer is PENDING until approved, Cash is PAID immediately
        if (payment.getPaymentMethod() == PaymentMethod.BANK_TRANSFER) {
            payment.setStatus(PaymentStatus.PENDING);
        } else {
            payment.setStatus(PaymentStatus.PAID);
        }

        return convertToDTO(paymentRepository.save(payment));
    }

    public PaymentDTO approvePayment(String id) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment.setStatus(PaymentStatus.PAID);
                    return convertToDTO(paymentRepository.save(payment));
                })
                .orElse(null);
    }

    public PaymentDTO rejectPayment(String id) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment.setStatus(PaymentStatus.REJECTED);
                    return convertToDTO(paymentRepository.save(payment));
                })
                .orElse(null);
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

    public PaymentDTO updatePayment(String id, PaymentDTO paymentDTO) {
        if (!paymentRepository.existsById(id)) return null;
        Payment updated = convertToEntity(paymentDTO);
        updated.setId(id);
        return convertToDTO(paymentRepository.save(updated));
    }

    public java.util.Map<String, Double> getMonthlyRevenue() {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        java.util.Map<String, Double> revenueMap = new java.util.LinkedHashMap<>();
        for (String month : months) {
            revenueMap.put(month, 0.0);
        }

        paymentRepository.findAll().stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID && p.getPaymentDate() != null)
                .forEach(p -> {
                    try {
                        String dateStr = p.getPaymentDate();
                        if (dateStr.contains("T")) {
                            dateStr = dateStr.split("T")[0];
                        }
                        LocalDate date = LocalDate.parse(dateStr);
                        // Only include current year's data
                        if (date.getYear() == LocalDate.now().getYear()) {
                            String month = date.getMonth().name().substring(0, 3);
                            month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
                            revenueMap.put(month, revenueMap.getOrDefault(month, 0.0) + p.getAmount());
                        }
                    } catch (Exception e) {
                        // Skip invalid dates
                    }
                });
        return revenueMap;
    }

    public java.util.Map<String, Double> getWeeklyRevenue() {
        java.util.Map<String, Double> revenueMap = new java.util.LinkedHashMap<>();
        for (int i = 1; i <= 5; i++) {
            revenueMap.put("Week " + i, 0.0);
        }

        paymentRepository.findAll().stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID && p.getPaymentDate() != null)
                .forEach(p -> {
                    try {
                        String dateStr = p.getPaymentDate();
                        if (dateStr.contains("T")) {
                            dateStr = dateStr.split("T")[0];
                        }
                        LocalDate date = LocalDate.parse(dateStr);
                        // Only for current month or just aggregate regardless?
                        // User likely wants current month's weeks.
                        if (date.getMonth() == LocalDate.now().getMonth() && date.getYear() == LocalDate.now().getYear()) {
                            String week = "Week " + ((date.getDayOfMonth() - 1) / 7 + 1);
                            revenueMap.put(week, revenueMap.getOrDefault(week, 0.0) + p.getAmount());
                        }
                    } catch (Exception e) {
                        // Skip invalid dates
                    }
                });
        return revenueMap;
    }

    private PaymentDTO convertToDTO(Payment p) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(p.getId());
        dto.setStudentId(p.getStudentId());
        dto.setPackageId(p.getPackageId());
        dto.setAmount(p.getAmount());
        dto.setPaymentDate(p.getPaymentDate());
        dto.setPaymentMethod(p.getPaymentMethod() != null ? p.getPaymentMethod().name() : null);
        dto.setStatus(p.getStatus());
        dto.setCardNumber(p.getCardNumber());
        dto.setCardHolder(p.getCardHolder());
        dto.setExpiryDate(p.getExpiryDate());
        dto.setReferenceNumber(p.getReferenceNumber());

        if (p.getStudentId() != null) {
            studentRepository.findById(p.getStudentId())
                    .ifPresent(student -> dto.setStudentName(student.getFullName()));
        }

        if (p.getPackageId() != null) {
            coursePackageRepository.findById(p.getPackageId())
                    .ifPresent(pkg -> dto.setPackageName(pkg.getName()));
        }

        return dto;
    }


    private Payment convertToEntity(PaymentDTO dto) {
        Payment p = new Payment();
        if (dto.getId() != null && !dto.getId().trim().isEmpty()) {
            p.setId(dto.getId());
        }
        p.setStudentId(dto.getStudentId());
        p.setPackageId(dto.getPackageId());
        p.setAmount(dto.getAmount());
        p.setPaymentDate(dto.getPaymentDate());
        if (dto.getPaymentMethod() != null) {
            try {
                p.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
            } catch (IllegalArgumentException e) {
                p.setPaymentMethod(PaymentMethod.CASH);
            }
        }
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : PaymentStatus.PAID);
        p.setCardNumber(dto.getCardNumber());
        p.setCardHolder(dto.getCardHolder());
        p.setExpiryDate(dto.getExpiryDate());
        p.setReferenceNumber(dto.getReferenceNumber());
        return p;
    }
}
