package com.project.cnh_manager.models;

public enum UserRole {

    ADMIN("admin"),
    USER("user"),
    INSTRUTOR("instrutor");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}