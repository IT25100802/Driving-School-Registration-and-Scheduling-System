package com.drivingschool.student.service;

import com.drivingschool.student.dto.StudentDTO;
import com.drivingschool.student.entity.Student;
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
        if (student.getEnrolledDate() == null || student.getEnrolledDate().isEmpty()) {
            student.setEnrolledDate(LocalDate.now().toString());
        }
        if (student.getStatus() == null || student.getStatus().isEmpty()) {
            student.setStatus("ACTIVE");
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
        return dto;
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setNic(dto.getNic());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setAddress(dto.getAddress());
        student.setLicenseCategory(dto.getLicenseCategory());
        student.setEnrolledDate(dto.getEnrolledDate());
        student.setStatus(dto.getStatus());
        return student;
    }
}
