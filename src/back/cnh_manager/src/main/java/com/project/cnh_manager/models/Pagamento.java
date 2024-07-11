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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "data", length = 100, nullable = false)
    @NotNull
    private LocalDate data;

    @Column(name = "horario", length = 100, nullable = false)
    @NotNull
    private LocalTime horario;

    @PrePersist
    protected void onCreate() {
        this.data = LocalDate.now();
        this.horario = LocalTime.now();
    }

    @Column(name = "statusPagamento", columnDefinition = "BOOLEAN DEFAULT false")
    @NotNull
    private boolean statusPagamento;

    @ManyToOne
    @JoinColumn(name = "tipo_pagamento_id", nullable = false)
    private TipoPagamento tipoPagamento;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    

    // @ManyToOne
    // @JoinColumn(name = "tipo_prova_id", nullable = false)
    // private TipoProva tipoProva;
    // @OneToOne(mappedBy = "pagamento")
    // private ProvaSolicitacao provaSolicitacao;

    public Pagamento(TipoPagamento tipoPagamento, User user) {
        this.tipoPagamento = tipoPagamento;
        this.user = user;
    }
}
