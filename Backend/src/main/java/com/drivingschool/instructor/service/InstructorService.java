package com.drivingschool.instructor.service;

import com.drivingschool.instructor.dto.InstructorDTO;
import com.drivingschool.instructor.entity.Instructor;
import com.drivingschool.instructor.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InstructorDTO getInstructorById(String id) {
        return instructorRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = convertToEntity(instructorDTO);
        if (instructor.getJoinedDate() == null || instructor.getJoinedDate().isEmpty()) {
            instructor.setJoinedDate(LocalDate.now().toString());
        }
        if (instructor.getStatus() == null || instructor.getStatus().isEmpty()) {
            instructor.setStatus("ACTIVE");
        }
        return convertToDTO(instructorRepository.save(instructor));
    }

    public InstructorDTO updateInstructor(String id, InstructorDTO instructorDTO) {
        if (!instructorRepository.existsById(id)) return null;
        
        Instructor updated = convertToEntity(instructorDTO);
        updated.setId(id);
        return convertToDTO(instructorRepository.save(updated));
    }

    public void deleteInstructor(String id) {
        instructorRepository.deleteById(id);
    }


    private InstructorDTO convertToDTO(Instructor instructor) {
        InstructorDTO dto = new InstructorDTO();
        dto.setId(instructor.getId());
        dto.setFullName(instructor.getFullName());
        dto.setEmail(instructor.getEmail());
        dto.setPhone(instructor.getPhone());
        dto.setNic(instructor.getNic());
        dto.setLicenseNumber(instructor.getLicenseNumber());
        dto.setSpecialization(instructor.getSpecialization());
        dto.setYearsOfExperience(instructor.getYearsOfExperience());
        dto.setStatus(instructor.getStatus());
        dto.setJoinedDate(instructor.getJoinedDate());
        return dto;
    }

    private Instructor convertToEntity(InstructorDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setId(dto.getId());
        instructor.setFullName(dto.getFullName());
        instructor.setEmail(dto.getEmail());
        instructor.setPhone(dto.getPhone());
        instructor.setNic(dto.getNic());
        instructor.setLicenseNumber(dto.getLicenseNumber());
        instructor.setSpecialization(dto.getSpecialization());
        instructor.setYearsOfExperience(dto.getYearsOfExperience());
        instructor.setStatus(dto.getStatus());
        instructor.setJoinedDate(dto.getJoinedDate());
        return instructor;
    }
}
