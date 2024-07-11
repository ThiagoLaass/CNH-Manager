

package com.project.cnh_manager.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitacoesRequestDTO(
        @NotNull
        String statusAberta,

        @NotBlank
        String tipoAula,

        @NotBlank
        String descricao
) {
}

