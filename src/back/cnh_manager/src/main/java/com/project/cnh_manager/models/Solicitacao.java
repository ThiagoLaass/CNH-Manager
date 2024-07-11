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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "solicitacao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Solicitacao {

    public interface CreateSolicitacao {
    }
    public interface UpdateSolicitacao {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;


    @Column(name = "solicitacaoAberta", columnDefinition = "BOOLEAN DEFAULT true")
    @NotNull
    private boolean statusAberta;

    @Column(name = "statusAprovada", columnDefinition = "BOOLEAN DEFAULT false")
    @NotNull
    private boolean statusAprovada;

    @Column(name = "descricao", length = 300)
    @NotNull(groups = {CreateSolicitacao.class})
    @NotEmpty(groups = {CreateSolicitacao.class})
    @Size(groups = {CreateSolicitacao.class, UpdateSolicitacao.class}, min = 5, max = 300)
    private String descricao;

    @Column(name = "horas_solicitadas", length = 300)
    @NotNull(groups = {CreateSolicitacao.class})
    @NotEmpty(groups = {CreateSolicitacao.class})
    @Size(groups = {CreateSolicitacao.class, UpdateSolicitacao.class}, min = 5, max = 300)
    private Integer horasSolicitadas;

    
    @Transient
    private String aulaId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name= "aula_id")
    private Aula aula;

    @Override
    public String toString() {
        return "Solicitacao [ID=" + id + "\n Solicitacao aberta=" + statusAberta + "\n Tipo da aula=" + aula.getNome() + "\n Solicitacao aprovada=" + statusAprovada  + ", Descricao="
                + descricao + " usuario que fez: " + user.getLogin() +" ]";
    }

 
}