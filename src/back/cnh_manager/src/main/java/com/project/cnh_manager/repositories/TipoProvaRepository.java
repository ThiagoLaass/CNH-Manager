package com.project.cnh_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cnh_manager.models.TipoProva;

@Repository
public interface TipoProvaRepository extends JpaRepository<TipoProva, Long>{

}
