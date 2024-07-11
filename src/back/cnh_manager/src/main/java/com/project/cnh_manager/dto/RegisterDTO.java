package com.project.cnh_manager.dto;

import com.project.cnh_manager.models.UserRole;

public record RegisterDTO(String login, String email, String password, UserRole role) {


}
