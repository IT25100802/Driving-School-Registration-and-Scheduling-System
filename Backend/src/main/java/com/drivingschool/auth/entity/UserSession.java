package com.drivingschool.auth.entity;

import com.drivingschool.admin.entity.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@SessionScope
public class UserSession {
    private String userId;
    private String username;
    private String fullName;
    private AdminRole role;
    private boolean loggedIn = false;

    public void logout() {
        this.userId = null;
        this.username = null;
        this.fullName = null;
        this.role = null;
        this.loggedIn = false;
    }

}
