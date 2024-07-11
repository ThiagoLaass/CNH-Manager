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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "horario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class HorarioAulaPratica{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable=false)
    private UUID codHorario;

    @Column(name = "hora_inicio", nullable=false)
    private LocalTime horaInicio;

    @Column(name= "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(name = "data", nullable=false)
    private LocalDate data;

    @Column(name = "status_aberto", columnDefinition = "BOOLEAN DEFAULT true")
    private boolean statusAberto;

    @Column(name= "status_aprovado", columnDefinition= "BOOLEAN DEFAULT false")
    private boolean statusAprovado;

    @Column(name = "aguardando_aprovacao", columnDefinition= "BOOLEAN DEFAULT false")
    private boolean aguardandoAprovacao;

    @Column(name = "aula_adicional", columnDefinition= "BOOLEAN DEFAULT false")
    private boolean aulaAdicional;

    @ManyToOne
    @JoinColumn(name = "instrutor", nullable = true)
    private User instrutor;

    @ManyToOne
    @JoinColumn(name = "aluno")
    private User aluno;

    @OneToOne
    @JoinColumn(name = "pagamento", nullable = true)
    private Pagamento pagamento;
}