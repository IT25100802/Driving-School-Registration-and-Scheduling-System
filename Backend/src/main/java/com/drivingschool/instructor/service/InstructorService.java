package com.drivingschool.instructor.service;

import com.drivingschool.instructor.dto.InstructorDTO;
import com.drivingschool.instructor.entity.Instructor;
import com.drivingschool.instructor.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

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
        Instructor lastIns = instructorRepository.findLastManualId();
        String nextId = "I0001";
        if (lastIns != null && lastIns.getId() != null) {
            try {
                String numericPart = lastIns.getId().replaceAll("\\D", "");
                if (!numericPart.isEmpty()) {
                    int lastNum = Integer.parseInt(numericPart);
                    nextId = String.format("I%04d", lastNum + 1);
                }
            } 
        }

        Instructor instructor = convertToEntity(instructorDTO);
        instructor.setId(nextId);
        
        if (instructor.getJoinedDate() == null || instructor.getJoinedDate().trim().isEmpty()) {
            instructor.setJoinedDate(LocalDate.now().toString());
        }
        if (instructor.getStatus() == null || instructor.getStatus().trim().isEmpty()) {
            instructor.setStatus("AVAILABLE");
        }
        return convertToDTO(instructorRepository.save(instructor));
    }

    public InstructorDTO updateInstructor(String id, InstructorDTO instructorDTO) {
        return instructorRepository.findById(id)
                .map(existing -> {
                    // Only update if the new value is not null/empty
                    if (instructorDTO.getFullName() != null) existing.setFullName(instructorDTO.getFullName());
                    if (instructorDTO.getEmail() != null) existing.setEmail(instructorDTO.getEmail());
                    if (instructorDTO.getPhone() != null) existing.setPhone(instructorDTO.getPhone());
                    if (instructorDTO.getNic() != null) existing.setNic(instructorDTO.getNic());
                    if (instructorDTO.getLicenseNumber() != null) existing.setLicenseNumber(instructorDTO.getLicenseNumber());
                    if (instructorDTO.getSpecialization() != null) existing.setSpecialization(instructorDTO.getSpecialization());
                    if (instructorDTO.getYearsOfExperience() > 0) existing.setYearsOfExperience(instructorDTO.getYearsOfExperience());
                    if (instructorDTO.getJoinedDate() != null) existing.setJoinedDate(instructorDTO.getJoinedDate());
                    if (instructorDTO.getStatus() != null) existing.setStatus(instructorDTO.getStatus());
                    
                    return convertToDTO(instructorRepository.save(existing));
                })
                .orElse(null);
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
        dto.setUsername(instructor.getUsername());
        dto.setRole(instructor.getRole());
        return dto;
    }

    private Instructor convertToEntity(InstructorDTO dto) {
        Instructor instructor = new Instructor();
        if (dto.getId() != null && !dto.getId().trim().isEmpty()) {
            instructor.setId(dto.getId());
        }
        instructor.setFullName(dto.getFullName());
        instructor.setEmail(dto.getEmail());
        instructor.setPhone(dto.getPhone());
        
        instructor.setLicenseNumber(dto.getLicenseNumber());
        instructor.setSpecialization(dto.getSpecialization());
        instructor.setYearsOfExperience(dto.getYearsOfExperience());
        instructor.setStatus(dto.getStatus() != null && !dto.getStatus().trim().isEmpty() ? dto.getStatus() : "AVAILABLE");
        instructor.setJoinedDate(dto.getJoinedDate());
        instructor.setUsername(dto.getUsername());
        instructor.setRole(dto.getRole() != null ? dto.getRole() : "INSTRUCTOR");
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            instructor.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return instructor;
    }
}
