package com.project.cnh_manager.dto;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSolicitacaoDTO {
    private UUID codSolicitacao;
    private boolean statusAberta;
    private String tipoAula;
    private String descricao;
    private UserDTO user;
}
