package com.project.cnh_manager.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.project.cnh_manager.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDTO{
        private UUID codHorario;
        private LocalTime hora;
        private LocalDate data;
        private User instrutor;
}