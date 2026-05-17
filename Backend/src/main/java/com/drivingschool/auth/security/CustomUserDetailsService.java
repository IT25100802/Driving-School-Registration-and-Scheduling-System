package com.drivingschool.auth.security;

import com.drivingschool.admin.entity.Admin;
import com.drivingschool.admin.repository.AdminRepository;
import com.drivingschool.instructor.entity.Instructor;
import com.drivingschool.instructor.repository.InstructorRepository;
import com.drivingschool.student.entity.Student;
import com.drivingschool.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try finding Admin by username
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return new User(admin.getUsername(), admin.getPassword() != null ? admin.getPassword() : "",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        // Try finding Student by email
        Student student = studentRepository.findByEmail(username);
        if (student != null) {
            return new User(student.getEmail(), student.getPassword() != null ? student.getPassword() : "",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_STUDENT")));
        }

        // Try finding Instructor by email
        Instructor instructor = instructorRepository.findByEmail(username);
        if (instructor != null) {
            return new User(instructor.getEmail(), instructor.getPassword() != null ? instructor.getPassword() : "",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_INSTRUCTOR")));
        }

        throw new UsernameNotFoundException("User not found with username/email: " + username);
    }
}
