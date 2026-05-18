package com.drivingschool.admin.service;

import com.drivingschool.admin.dto.AdminDTO;
import com.drivingschool.admin.entity.Admin;
import com.drivingschool.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdminDTO getAdminById(String id) {
        return adminRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public AdminDTO createAdmin(AdminDTO adminDTO) {
        Admin lastAdmin = adminRepository.findFirstByIdStartingWithOrderByIdDesc("A");
        String nextId = "A0001";

        if (lastAdmin != null && lastAdmin.getId() != null) {
            String lastId = lastAdmin.getId();
            if (lastId.startsWith("A")) {
                try {
                    int lastNum = Integer.parseInt(lastId.substring(1));
                    nextId = String.format("A%04d", lastNum + 1);
                } catch (NumberFormatException e) {
                    System.out.println("ID parsing failed for: " + lastId);
                }
            }
        }

        Admin admin = convertToEntity(adminDTO);
        admin.setId(nextId);
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        return convertToDTO(adminRepository.save(admin));
    }

    public AdminDTO updateAdmin(String id, AdminDTO adminDTO) {
        return adminRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(adminDTO.getFullName());
                    existing.setUsername(adminDTO.getUsername());
                    existing.setEmail(adminDTO.getEmail());
                    existing.setPhone(adminDTO.getPhone());
                    existing.setRole(adminDTO.getRole());
                    if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
                        existing.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
                    }
                    return convertToDTO(adminRepository.save(existing));
                })
                .orElse(null);
    }

    public void deleteAdmin(String id) {
        adminRepository.deleteById(id);
    }

    private AdminDTO convertToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setFullName(admin.getFullName());
        dto.setEmail(admin.getEmail());
        dto.setPhone(admin.getPhone());
        dto.setRole(admin.getRole());
        return dto;
    }

    private Admin convertToEntity(AdminDTO dto) {
        Admin admin = new Admin();
        if (dto.getId() != null && !dto.getId().trim().isEmpty()) {
            admin.setId(dto.getId());
        }
        admin.setUsername(dto.getUsername());
        // Password encoding is handled in create/update methods
        admin.setPassword(dto.getPassword());
        admin.setFullName(dto.getFullName());
        admin.setEmail(dto.getEmail());
        admin.setPhone(dto.getPhone());
        admin.setRole(dto.getRole());
        return admin;
    }
}
