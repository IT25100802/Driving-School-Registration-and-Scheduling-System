package com.drivingschool.schedule.controller;

import com.drivingschool.schedule.dto.ScheduleDTO;
import com.drivingschool.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable String id) {
        ScheduleDTO schedule = scheduleService.getScheduleById(id);
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.createSchedule(scheduleDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable String id, @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO updated = scheduleService.updateSchedule(id, scheduleDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/today")
    public List<ScheduleDTO> getTodaySchedules() {
        return scheduleService.getTodaySchedules();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }
}
