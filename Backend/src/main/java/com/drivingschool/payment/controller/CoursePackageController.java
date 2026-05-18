package com.drivingschool.payment.controller;

import com.drivingschool.payment.dto.CoursePackageDTO;
import com.drivingschool.payment.service.CoursePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin(origins = "*")
public class CoursePackageController {

    @Autowired
    private CoursePackageService packageService;

    @GetMapping
    public List<CoursePackageDTO> getAllPackages() {
        return packageService.getAllPackages();
    }

    @GetMapping("/{id}")
    public CoursePackageDTO getPackageById(@PathVariable String id) {
        return packageService.getPackageById(id);
    }

    @PostMapping
    public CoursePackageDTO createPackage(@RequestBody CoursePackageDTO packageDTO) {
        return packageService.createPackage(packageDTO);
    }

    @PutMapping("/{id}")
    public CoursePackageDTO updatePackage(@PathVariable String id, @RequestBody CoursePackageDTO packageDTO) {
        return packageService.updatePackage(id, packageDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePackage(@PathVariable String id) {
        packageService.deletePackage(id);
    }
}
