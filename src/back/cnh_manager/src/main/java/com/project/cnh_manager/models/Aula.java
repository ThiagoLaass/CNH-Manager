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


@Entity // trata como uma tabela
@Table(name = "aula")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String nome;

    @Column(name = "carga_horaria", length = 100, nullable = false)
    @NotNull
    private Integer cargaHoraria;

    @OneToMany(mappedBy = "aula")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<CargaHorariaConcluida> solicitacoes_prova = new ArrayList<CargaHorariaConcluida>();

    @OneToMany
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();


}
