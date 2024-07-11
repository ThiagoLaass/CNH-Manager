package com.project.cnh_manager.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prova")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "prova_id")
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prova_id", unique = true, nullable=false)
    private UUID id;

    @Column(name = "statusAprovado", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean statusAprovado;

    @Column(name = "data", nullable=true)
    private LocalDate data;

    @Column(name = "horario", nullable=true)
    private LocalTime horario;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tipo_prova_id", nullable = false)
    private TipoProva tipoProva;


    public Prova(User user, TipoProva tipoProva){
        this.user = user;
        this.tipoProva = tipoProva;
    }
}
