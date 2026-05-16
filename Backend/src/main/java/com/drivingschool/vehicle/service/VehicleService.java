package com.drivingschool.vehicle.service;

import com.drivingschool.vehicle.dto.VehicleDTO;
import com.drivingschool.vehicle.entity.Vehicle;
import com.drivingschool.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO getVehicleById(String id) {
        return vehicleRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        Vehicle lastVehicle = vehicleRepository.findLastManualId();
        String nextId = "V0001";
        if (lastVehicle != null && lastVehicle.getId() != null) {
            try {
                String numericPart = lastVehicle.getId().replaceAll("\\D", "");
                if (!numericPart.isEmpty()) {
                    int lastNum = Integer.parseInt(numericPart);
                    nextId = String.format("V%04d", lastNum + 1);
                }
            } catch (Exception e) {
                // Fallback to V0001
            }
        }

        Vehicle vehicle = convertToEntity(vehicleDTO);
        vehicle.setId(nextId);
        if (vehicle.getStatus() == null || vehicle.getStatus().trim().isEmpty()) {
            vehicle.setStatus("AVAILABLE");
        }
        return convertToDTO(vehicleRepository.save(vehicle));
    }

    public VehicleDTO updateVehicle(String id, VehicleDTO vehicleDTO) {
        return vehicleRepository.findById(id)
                .map(existing -> {
                    if (vehicleDTO.getRegistrationNumber() != null) existing.setRegistrationNumber(vehicleDTO.getRegistrationNumber());
                    if (vehicleDTO.getMake() != null) existing.setMake(vehicleDTO.getMake());
                    if (vehicleDTO.getModel() != null) existing.setModel(vehicleDTO.getModel());
                    if (vehicleDTO.getYear() > 0) existing.setYear(vehicleDTO.getYear());
                    if (vehicleDTO.getVehicleType() != null) existing.setVehicleType(vehicleDTO.getVehicleType());
                    if (vehicleDTO.getAssignedCategory() != null) existing.setAssignedCategory(vehicleDTO.getAssignedCategory());
                    if (vehicleDTO.getStatus() != null) existing.setStatus(vehicleDTO.getStatus());
                    
                    return convertToDTO(vehicleRepository.save(existing));
                })
                .orElse(null);
    }

    public void deleteVehicle(String id) {
        vehicleRepository.deleteById(id);
    }


    private VehicleDTO convertToDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setRegistrationNumber(vehicle.getRegistrationNumber());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setAssignedCategory(vehicle.getAssignedCategory());
        dto.setStatus(vehicle.getStatus());
        return dto;
    }

    private Vehicle convertToEntity(VehicleDTO dto) {
        Vehicle vehicle = new Vehicle();
        if (dto.getId() != null && !dto.getId().trim().isEmpty()) {
            vehicle.setId(dto.getId());
        }
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setAssignedCategory(dto.getAssignedCategory());
        vehicle.setStatus(dto.getStatus() != null && !dto.getStatus().trim().isEmpty() ? dto.getStatus() : "AVAILABLE");
        return vehicle;
    }
}
