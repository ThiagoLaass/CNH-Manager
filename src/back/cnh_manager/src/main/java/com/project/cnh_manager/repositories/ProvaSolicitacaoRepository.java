package com.project.cnh_manager.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cnh_manager.models.ProvaSolicitacao;

@Repository
public interface ProvaSolicitacaoRepository extends JpaRepository<ProvaSolicitacao, UUID> {
    ProvaSolicitacao findByUserId(UUID userId);

    ProvaSolicitacao findByProvaId(UUID provaId);
    
}
