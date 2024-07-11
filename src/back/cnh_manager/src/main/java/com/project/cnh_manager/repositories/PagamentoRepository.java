package com.project.cnh_manager.repositories;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cnh_manager.models.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID>{
    Pagamento findPagamentoById(UUID id);  
}
