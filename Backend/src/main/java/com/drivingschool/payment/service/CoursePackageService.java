package com.drivingschool.payment.service;

import com.drivingschool.payment.dto.CoursePackageDTO;
import com.drivingschool.payment.entity.CoursePackage;
import com.drivingschool.payment.repository.CoursePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursePackageService {

    @Autowired
    private CoursePackageRepository packageRepository;

    public List<CoursePackageDTO> getAllPackages() {
        return packageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CoursePackageDTO getPackageById(String id) {
        return packageRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public CoursePackageDTO createPackage(CoursePackageDTO dto) {
        CoursePackage entity = convertToEntity(dto);
        return convertToDTO(packageRepository.save(entity));
    }

    public CoursePackageDTO updatePackage(String id, CoursePackageDTO dto) {
        if (!packageRepository.existsById(id)) return null;
        CoursePackage updated = convertToEntity(dto);
        updated.setId(id);
        return convertToDTO(packageRepository.save(updated));
    }

    public void deletePackage(String id) {
        packageRepository.deleteById(id);
    }

    private CoursePackageDTO convertToDTO(CoursePackage entity) {
        CoursePackageDTO dto = new CoursePackageDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setTheoryHours(entity.getTheoryHours());
        dto.setPracticalHours(entity.getPracticalHours());
        dto.setLicenseCategory(entity.getLicenseCategory());
        return dto;
    }

    private CoursePackage convertToEntity(CoursePackageDTO dto) {
        CoursePackage entity = new CoursePackage();
        if (dto.getId() != null && !dto.getId().trim().isEmpty()) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setTheoryHours(dto.getTheoryHours());
        entity.setPracticalHours(dto.getPracticalHours());
        entity.setLicenseCategory(dto.getLicenseCategory());
        return entity;
    }
}
