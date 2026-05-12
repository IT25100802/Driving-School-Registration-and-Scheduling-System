package com.drivingschool.instructor.controller;

import com.drivingschool.instructor.dto.InstructorDTO;
import com.drivingschool.instructor.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@CrossOrigin(origins = "*")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public List<InstructorDTO> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable String id) {
        InstructorDTO instructor = instructorService.getInstructorById(id);
        return instructor != null ? ResponseEntity.ok(instructor) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public InstructorDTO createInstructor(@RequestBody InstructorDTO instructorDTO) {
        return instructorService.createInstructor(instructorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(@PathVariable String id,
            @RequestBody InstructorDTO instructorDTO) {
        InstructorDTO updated = instructorService.updateInstructor(id, instructorDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable String id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.ok().build();
    }

    // ✨ NEW: Search instructors by name or specialization
    @GetMapping("/search")
    public List<InstructorDTO> searchInstructors(@RequestParam String keyword) {
        return instructorService.searchInstructors(keyword);
    }

    // ✨ NEW: Get only ACTIVE instructors
    @GetMapping("/active")
    public List<InstructorDTO> getActiveInstructors() {
        return instructorService.getActiveInstructors();
    }
}
