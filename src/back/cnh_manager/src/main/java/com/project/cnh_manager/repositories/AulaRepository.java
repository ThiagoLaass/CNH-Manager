
package com.project.cnh_manager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cnh_manager.models.Aula;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    @Override
    long count();
    
    Aula findByNome(String nome);
}
