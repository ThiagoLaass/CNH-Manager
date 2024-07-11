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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "avaliacao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Avaliacao {

    public interface CreateAvaliacao {

    }

    public interface UpdateAvaliacao {
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "descricao", length = 300)
    @NotNull(groups = { CreateAvaliacao.class })
    @NotEmpty(groups = { CreateAvaliacao.class })
    @Size(groups = { CreateAvaliacao.class, UpdateAvaliacao.class }, min = 5, max = 300)
    private String descricao;

    @Column(name = "num_avaliacao", length = 300)
    @NotNull(groups = {CreateAvaliacao.class})
    @NotEmpty(groups = {CreateAvaliacao.class})
    @DecimalMin(value = "0.0")
    private double numAvaliacao;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}