
package com.project.cnh_manager.repositories;


import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.models.CargaHorariaConcluida;
import com.project.cnh_manager.models.User;

@Repository
public interface CargaHorariaConcluidaRepository extends JpaRepository<CargaHorariaConcluida, UUID> {
    ArrayList<CargaHorariaConcluida> findByAula( Aula aula);
    ArrayList<CargaHorariaConcluida> findByUser(User user);
}
