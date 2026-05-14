package com.drivingschool.auth.controller;

import com.drivingschool.admin.entity.Admin;
import com.drivingschool.admin.entity.AdminRole;
import com.drivingschool.admin.repository.AdminRepository;
import com.drivingschool.auth.security.JwtUtil;
import com.drivingschool.instructor.entity.Instructor;
import com.drivingschool.instructor.dto.InstructorDTO;
import com.drivingschool.instructor.repository.InstructorRepository;
import com.drivingschool.student.dto.StudentDTO;
import com.drivingschool.student.entity.Student;
import com.drivingschool.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private com.drivingschool.student.service.StudentService studentService;

    @Autowired
    private com.drivingschool.instructor.service.InstructorService instructorService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            String token = jwtUtil.generateToken(userDetails, role);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", role);
            response.put("username", username);

            // Fetch full name based on role
            if ("ROLE_ADMIN".equals(role)) {
                Admin admin = adminRepository.findByUsername(username);
                response.put("fullName", admin.getFullName());
                response.put("id", admin.getId());
            } else if ("ROLE_STUDENT".equals(role)) {
                Student student = studentRepository.findByEmail(username);
                response.put("fullName", student.getFullName());
                response.put("id", student.getId());
            } else if ("ROLE_INSTRUCTOR".equals(role)) {
                Instructor instructor = instructorRepository.findByEmail(username);
                response.put("fullName", instructor.getFullName());
                response.put("id", instructor.getId());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> data) {
        String role = data.get("role"); // "STUDENT" or "INSTRUCTOR"
        String email = data.get("email");
        String password = data.get("password");
        String fullName = data.get("fullName");

        if (studentRepository.findByEmail(email) != null || instructorRepository.findByEmail(email) != null
                || adminRepository.findByUsername(email) != null) {
            return ResponseEntity.badRequest().body("Email/Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        if ("STUDENT".equalsIgnoreCase(role)) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setEmail(email);
            studentDTO.setPassword(password); // Service will encode it
            studentDTO.setFullName(fullName);
            studentDTO.setPhone(data.get("phone"));
            studentDTO.setUsername(email);
            studentDTO.setStatus("ACTIVE");
            studentService.createStudent(studentDTO);
            return ResponseEntity.ok("Student registered successfully");
        } else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setEmail(email);
            instructorDTO.setPassword(password); // Service will encode it
            instructorDTO.setFullName(fullName);
            instructorDTO.setPhone(data.get("phone"));
            instructorDTO.setUsername(email);
            instructorDTO.setStatus("ACTIVE");
            instructorService.createInstructor(instructorDTO);
            return ResponseEntity.ok("Instructor registered successfully");
        } else if ("ADMIN".equalsIgnoreCase(role)) {
            Admin admin = new Admin();
            admin.setUsername(email);
            admin.setPassword(encodedPassword);
            admin.setFullName(fullName);
            admin.setRole(AdminRole.ADMIN);
            adminRepository.save(admin);
            return ResponseEntity.ok("Admin registered successfully");
        }

        return ResponseEntity.badRequest().body("Invalid role");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

}
