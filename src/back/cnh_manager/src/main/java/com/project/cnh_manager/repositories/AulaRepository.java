
package com.project.cnh_manager.repositories;


import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.cnh_manager.models.Aula;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    long count();
}
