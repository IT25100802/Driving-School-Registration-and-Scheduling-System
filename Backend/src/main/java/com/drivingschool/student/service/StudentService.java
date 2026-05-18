package com.drivingschool.student.service;

import com.drivingschool.student.dto.StudentDTO;
import com.drivingschool.student.entity.Student;
import com.drivingschool.payment.repository.CoursePackageRepository;
import com.drivingschool.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CoursePackageRepository packageRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(String id) {
        return studentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student lastStudent = studentRepository.findLastManualId();
        String nextId = "S0001";
        if (lastStudent != null && lastStudent.getId() != null) {
            try {
                String numericPart = lastStudent.getId().replaceAll("\\D", "");
                if (!numericPart.isEmpty()) {
                    int lastNum = Integer.parseInt(numericPart);
                    nextId = String.format("S%04d", lastNum + 1);
                }
            } catch (Exception e) {
                // Fallback to S0001
            }
        }

        Student student = convertToEntity(studentDTO);
        student.setId(nextId);

        if (student.getEnrolledDate() == null || student.getEnrolledDate().trim().isEmpty()) {
            student.setEnrolledDate(LocalDate.now().toString());
        }
        if (student.getStatus() == null || student.getStatus().trim().isEmpty()) {
            student.setStatus("ACTIVE");
        }
        if (student.getTrainingPhase() == null) {
            student.setTrainingPhase(com.drivingschool.student.entity.TrainingPhase.REGISTRATION);
        }

        // Auto-set license category from package if missing
        if (student.getLicenseCategory() == null && student.getPackageId() != null) {
            packageRepository.findById(student.getPackageId())
                    .ifPresent(pkg -> student.setLicenseCategory(pkg.getLicenseCategory()));
        }
        return convertToDTO(studentRepository.save(student));
    }

    public StudentDTO updateStudent(String id, StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .map(existing -> {
                    if (studentDTO.getFullName() != null) existing.setFullName(studentDTO.getFullName());
                    if (studentDTO.getEmail() != null) existing.setEmail(studentDTO.getEmail());
                    if (studentDTO.getPhone() != null) existing.setPhone(studentDTO.getPhone());
                    if (studentDTO.getNic() != null) existing.setNic(studentDTO.getNic());
                    if (studentDTO.getDateOfBirth() != null) existing.setDateOfBirth(studentDTO.getDateOfBirth());
                    if (studentDTO.getAddress() != null) existing.setAddress(studentDTO.getAddress());
                    if (studentDTO.getLicenseCategory() != null) existing.setLicenseCategory(studentDTO.getLicenseCategory());
                    if (studentDTO.getEnrolledDate() != null) existing.setEnrolledDate(studentDTO.getEnrolledDate());
                    if (studentDTO.getStatus() != null) existing.setStatus(studentDTO.getStatus());
                    if (studentDTO.getTrainingPhase() != null) existing.setTrainingPhase(studentDTO.getTrainingPhase());
                    if (studentDTO.getPackageId() != null) existing.setPackageId(studentDTO.getPackageId());

                    return convertToDTO(studentRepository.save(existing));
                })
                .orElse(null);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }


    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFullName(student.getFullName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setNic(student.getNic());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setAddress(student.getAddress());
        dto.setLicenseCategory(student.getLicenseCategory());
        dto.setEnrolledDate(student.getEnrolledDate());
        dto.setStatus(student.getStatus());
        dto.setTrainingPhase(student.getTrainingPhase());
        dto.setPackageId(student.getPackageId());

        if (student.getPackageId() != null && !student.getPackageId().trim().isEmpty()) {
            packageRepository.findById(student.getPackageId())
                    .ifPresent(pkg -> dto.setPackageName(pkg.getName()));
        }

        dto.setUsername(student.getUsername());
        dto.setRole(student.getRole());

        return dto;
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        if (dto.getId() != null && !dto.getId().trim().isEmpty()) {
            student.setId(dto.getId());
        }
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setNic(dto.getNic());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setAddress(dto.getAddress());
        student.setLicenseCategory(dto.getLicenseCategory());
        student.setEnrolledDate(dto.getEnrolledDate());
        student.setStatus(dto.getStatus() != null && !dto.getStatus().trim().isEmpty() ? dto.getStatus() : "ACTIVE");
        student.setTrainingPhase(dto.getTrainingPhase() != null ? dto.getTrainingPhase() : com.drivingschool.student.entity.TrainingPhase.REGISTRATION);
        student.setPackageId(dto.getPackageId());
        student.setUsername(dto.getUsername());
        student.setRole(dto.getRole() != null ? dto.getRole() : "STUDENT");
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return student;
    }
}
