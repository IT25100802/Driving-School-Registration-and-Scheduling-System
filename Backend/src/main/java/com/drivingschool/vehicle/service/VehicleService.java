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
        Vehicle vehicle = convertToEntity(vehicleDTO);
        if (vehicle.getStatus() == null || vehicle.getStatus().isEmpty()) {
            vehicle.setStatus("AVAILABLE");
        }
        return convertToDTO(vehicleRepository.save(vehicle));
    }

    public VehicleDTO updateVehicle(String id, VehicleDTO vehicleDTO) {
        if (!vehicleRepository.existsById(id)) return null;
        
        Vehicle updated = convertToEntity(vehicleDTO);
        updated.setId(id);
        return convertToDTO(vehicleRepository.save(updated));
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
        dto.setLastServiceDate(vehicle.getLastServiceDate());
        return dto;
    }

    private Vehicle convertToEntity(VehicleDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setAssignedCategory(dto.getAssignedCategory());
        vehicle.setStatus(dto.getStatus());
        vehicle.setLastServiceDate(dto.getLastServiceDate());
        return vehicle;
    }
}
