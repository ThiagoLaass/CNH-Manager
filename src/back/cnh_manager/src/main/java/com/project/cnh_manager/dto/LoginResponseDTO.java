package com.project.cnh_manager.dto;

import java.util.UUID;

import com.project.cnh_manager.models.UserRole;

public record LoginResponseDTO(String token, UUID id, UserRole role) {
    
}
