
package com.project.cnh_manager.repositories;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cnh_manager.models.HorarioAulaPratica;

@Repository
public interface HorarioAulaPraticaRepository extends JpaRepository<HorarioAulaPratica, UUID> {

}
