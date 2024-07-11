package com.project.cnh_manager.dto;

import java.util.UUID;

import com.project.cnh_manager.models.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String login;
    private String email;
    private UserRole role;
    
}
