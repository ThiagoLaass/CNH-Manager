package com.project.cnh_manager.dto;

import java.util.UUID;

import com.project.cnh_manager.models.Aula;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.project.cnh_manager.models.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoDTO{
        private UUID id;
        private boolean statusAberta;
        private boolean statusAprovada;
        private String descricao;
        private Integer horasSolicitadas;
        private String aulaId;
        private User user;
        private Aula aula;

}
