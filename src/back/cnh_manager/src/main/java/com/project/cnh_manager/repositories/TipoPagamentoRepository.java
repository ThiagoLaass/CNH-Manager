package com.project.cnh_manager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cnh_manager.models.TipoPagamento;

@Repository
public interface TipoPagamentoRepository extends JpaRepository<TipoPagamento, Long>{
    TipoPagamento findByNome(String nome);
    
}
