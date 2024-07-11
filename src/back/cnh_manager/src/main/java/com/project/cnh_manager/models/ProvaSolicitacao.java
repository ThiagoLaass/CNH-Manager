package com.project.cnh_manager.models;

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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "prova_solicitacao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProvaSolicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private UUID id;

    // @Column(name = "tipo_prova", length = 100, nullable = false)
    // @NotNull
    // @NotEmpty
    // @Size(min = 2, max = 100)
    // private String tipoProva;

    @Transient
    private String idTipoProva;

    @Column(name = "descricao", length = 100)
    @Size(min = 2, max = 200)
    @NotNull
    private String descricao;

    @Column(name = "prova_elegivel", columnDefinition = "BOOLEAN DEFAULT false")
    @NotNull
    private boolean statusProvaElegivel;

    @Column(name = "solicitacaoAberta", columnDefinition = "BOOLEAN DEFAULT true")
    @NotNull
    private boolean statusAberta;

    @Column(name = "statusAprovada", columnDefinition = "BOOLEAN DEFAULT false")
    @NotNull
    private boolean statusAprovada;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tipo_prova_id", nullable = false)
    private TipoProva tipoProva;

    @OneToOne
    @JoinColumn(name="pagamento_id", referencedColumnName="id", nullable = true)
    private Pagamento pagamento;

    @OneToOne
    @JoinColumn(name="prova_id", referencedColumnName="prova_id", nullable = true)
    private Prova prova;

}