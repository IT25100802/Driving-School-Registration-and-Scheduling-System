package com.drivingschool.schedule.service;

import com.drivingschool.instructor.entity.Instructor;
import com.drivingschool.instructor.repository.InstructorRepository;
import com.drivingschool.schedule.dto.ScheduleDTO;
import com.drivingschool.schedule.entity.Schedule;
import com.drivingschool.schedule.repository.ScheduleRepository;
import com.drivingschool.student.entity.Student;
import com.drivingschool.student.repository.StudentRepository;
import com.drivingschool.vehicle.entity.Vehicle;
import com.drivingschool.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getTodaySchedules() {
        String today = java.time.LocalDate.now().toString();
        return scheduleRepository.findByScheduleDate(today).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ScheduleDTO getScheduleById(String id) {
        return scheduleRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        // Conflict checking logic should go here
        Schedule schedule = convertToEntity(scheduleDTO);
        if (schedule.getStatus() == null || schedule.getStatus().trim().isEmpty()) {
            schedule.setStatus("SCHEDULED");
        }
        return convertToDTO(scheduleRepository.save(schedule));
    }

    public ScheduleDTO updateSchedule(String id, ScheduleDTO scheduleDTO) {
        if (!scheduleRepository.existsById(id)) return null;

        Schedule updated = convertToEntity(scheduleDTO);
        updated.setId(id);
        return convertToDTO(scheduleRepository.save(updated));
    }

    public void deleteSchedule(String id) {
        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO convertToDTO(Schedule s) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(s.getId());
        dto.setStudentId(s.getStudentId());
        dto.setInstructorId(s.getInstructorId());
        dto.setVehicleId(s.getVehicleId());
        dto.setScheduleDate(s.getScheduleDate());
        dto.setStartTime(s.getStartTime());
        dto.setEndTime(s.getEndTime());
        dto.setScheduleType(s.getScheduleType());
        dto.setStatus(s.getStatus());
        dto.setNotes(s.getNotes());

        // Map names
        studentRepository.findById(s.getStudentId())
                .ifPresent(student -> dto.setStudentName(student.getFullName()));

        instructorRepository.findById(s.getInstructorId())
                .ifPresent(instructor -> dto.setInstructorName(instructor.getFullName()));

        vehicleRepository.findById(s.getVehicleId())
                .ifPresent(vehicle -> dto.setVehicleRegistration(vehicle.getRegistrationNumber()));

        return dto;
    }

    private Schedule convertToEntity(ScheduleDTO dto) {
        Schedule s = new Schedule();
        if (dto.getId() != null && !dto.getId().trim().isEmpty()) {
            s.setId(dto.getId());
        }
        s.setStudentId(dto.getStudentId());
        s.setInstructorId(dto.getInstructorId());
        s.setVehicleId(dto.getVehicleId());
        s.setScheduleDate(dto.getScheduleDate());
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());
        s.setScheduleType(dto.getScheduleType());
        s.setStatus(dto.getStatus() != null && !dto.getStatus().trim().isEmpty() ? dto.getStatus() : "SCHEDULED");
        s.setNotes(dto.getNotes());
        return s;
    }
}
