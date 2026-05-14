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
        Student student = convertToEntity(studentDTO);
        if (student.getEnrolledDate() == null || student.getEnrolledDate().trim().isEmpty()) {
            student.setEnrolledDate(LocalDate.now().toString());
        }
        if (student.getStatus() == null || student.getStatus().trim().isEmpty()) {
            student.setStatus("ACTIVE");
        }
        if (student.getTrainingPhase() == null) {
            student.setTrainingPhase(com.drivingschool.student.entity.TrainingPhase.REGISTRATION);
        }
        return convertToDTO(studentRepository.save(student));
    }

    public StudentDTO updateStudent(String id, StudentDTO studentDTO) {
        if (!studentRepository.existsById(id)) return null;

        Student updated = convertToEntity(studentDTO);
        updated.setId(id);
        return convertToDTO(studentRepository.save(updated));
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
        return student;
    }
}
