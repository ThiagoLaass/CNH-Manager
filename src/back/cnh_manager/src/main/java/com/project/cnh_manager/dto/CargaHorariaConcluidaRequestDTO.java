package com.project.cnh_manager.dto;

import com.project.cnh_manager.models.Aula;

public record  CargaHorariaConcluidaRequestDTO(String tipoAula, Integer cargaHoraria, Aula aula) {
    
}
