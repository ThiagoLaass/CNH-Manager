package com.project.cnh_manager.models;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "carga_horaria_concluida")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CargaHorariaConcluida{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private UUID cargaHorariaId;

    @Column(name = "carga_horaria", length = 100, nullable = false)
    @NotNull
    private int cargaHoraria;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    public CargaHorariaConcluida(int cargaHoraria, User user, Aula aula){
        this.cargaHoraria = cargaHoraria;
        this.user = user;
        this.aula = aula;

    }

    public void setCargaHoraria(Integer horasSolicitadas) {
        this.cargaHoraria += horasSolicitadas;
    }



}