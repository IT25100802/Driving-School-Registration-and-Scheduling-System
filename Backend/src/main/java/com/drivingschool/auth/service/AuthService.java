package com.drivingschool.auth.service;

import com.drivingschool.admin.entity.Admin;
import com.drivingschool.admin.repository.AdminRepository;
import com.drivingschool.auth.entity.UserSession;
import com.drivingschool.auth.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserSession userSession;

    public boolean login(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            userSession.setUserId(admin.getId());
            userSession.setUsername(admin.getUsername());
            userSession.setFullName(admin.getFullName());
            userSession.setRole(admin.getRole());
            userSession.setLoggedIn(true);
            return true;
        }
        return false;
    }

    public void logout() {
        userSession.logout();
    }

    public UserSession getCurrentSession() {
        return userSession;
    }
}
