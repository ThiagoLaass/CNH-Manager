
package com.project.cnh_manager.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestSolicitacaoDTO {
        private UUID codSolicitacao;
        private boolean statusAberta;

        @NotBlank(message = "Tipo da aula é obrigatório")
        private String tipoAula;
        private String descricao;

        @NotNull(message = "Id do usuário é obrigatório")
        private UUID idUser;
}

