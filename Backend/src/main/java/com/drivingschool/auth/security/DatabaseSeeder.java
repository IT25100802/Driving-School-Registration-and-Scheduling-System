package com.drivingschool.auth.security;

import com.drivingschool.admin.entity.Admin;
import com.drivingschool.admin.entity.AdminRole;
import com.drivingschool.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if the default admin already exists
        if (adminRepository.findByUsername("admin") == null) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            // The password "admin123" will be encoded using BCrypt
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("System Administrator");
            admin.setRole(AdminRole.ADMIN);
            admin.setId("A0000");

            adminRepository.save(admin);
            System.out.println("Default admin created successfully!");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
        }
    }
}
