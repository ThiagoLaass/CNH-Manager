package com.project.cnh_manager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.models.CargaHorariaConcluida;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.AulaRepository;
import com.project.cnh_manager.repositories.CargaHorariaConcluidaRepository;
import com.project.cnh_manager.repositories.UserRepository;

@Service
public class CargaHorariaConcluidaService {

    @Autowired
    private CargaHorariaConcluidaRepository cargaHorariaConcluidaRepository;

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private AulaRepository aulaRepository;

    public ArrayList<CargaHorariaConcluida> getCargasHorarias(UUID userId) throws ObjectNotFoundException {
        return cargaHorariaConcluidaRepository.findByUser(findUser(userId));
    }

    public User findUser(UUID userId){
        return userRepository.findUserById(userId);
    }

    public void setHorasPratica(User user, int horas){
        ArrayList<CargaHorariaConcluida> cargasHorarias = getCargasHorarias(user.getId());
        for (CargaHorariaConcluida cargaHorariaConcluida : cargasHorarias) {
            cargaHorariaConcluida.setCargaHoraria(horas);
            cargaHorariaConcluidaRepository.save(cargaHorariaConcluida);
        }
    }

    public boolean verificaHorasPraticas(User aluno){
        ArrayList<CargaHorariaConcluida> cargasHorarias = getCargasHorarias(aluno.getId());
        List<Aula> aulas = aulaRepository.findAll();
        for(Aula aula : aulas){
            if (aula.getNome().equals("Prática")){
                for (CargaHorariaConcluida cargaHorariaConcluida : cargasHorarias) {
                    if(Objects.equals(cargaHorariaConcluida.getAula().getId(), aula.getId())){
                        return cargaHorariaConcluida.getCargaHoraria() < aula.getCargaHoraria();
                    }
                } 
            }
        }

        return false;
    }
}
