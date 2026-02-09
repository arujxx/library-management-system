package com.company.security;

import com.company.models.User;

public class Session {

    private static Session instance;
    private User user;
    private Role role = Role.GUEST;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void login(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public void logout() {
        this.user = null;
        this.role = Role.GUEST;
    }

    public Role getRole() {
        return role;
    }

    public boolean hasRole(Role required) {
        return role.ordinal() >= required.ordinal();
    }
}
