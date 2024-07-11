package com.project.cnh_manager.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_prova")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class TipoProva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String nome;

    @OneToMany(mappedBy = "tipoProva")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Prova> provas = new ArrayList<Prova>();

    @OneToMany(mappedBy = "tipoProva")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<ProvaSolicitacao> solicitacoesDeProva = new ArrayList<ProvaSolicitacao>();


    // @OneToMany(mappedBy = "tipoProva")
    // @JsonProperty(access = Access.WRITE_ONLY)
    // private List<Pagamento> pagamentos = new ArrayList<Pagamento>();
    
}
