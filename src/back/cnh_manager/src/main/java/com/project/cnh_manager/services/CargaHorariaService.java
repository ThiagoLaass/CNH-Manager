package com.project.cnh_manager.services;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.exception.CustomServiceException;
import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.models.CargaHorariaConcluida;
import com.project.cnh_manager.models.HorarioAulaPratica;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.AulaRepository;
import com.project.cnh_manager.repositories.CargaHorariaConcluidaRepository;
import com.project.cnh_manager.repositories.UserRepository;

@Service
public class CargaHorariaService {

    @Autowired
    private CargaHorariaConcluidaRepository cargaHorariaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private UserRepository userRepository;

    public void inicializarCargaHorariaDoAluno(UUID userId) {

        List<Aula> aulas = aulaRepository.findAll();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomServiceException("Usuário não encontrado na inicialização da carga horária."));

        List<CargaHorariaConcluida> cargas = aulas.stream()
            .map(aula -> {
                CargaHorariaConcluida carga = new CargaHorariaConcluida(0, user, aula);
                carga.setCargaHoraria(0);
                return carga;
            })
            .collect(Collectors.toList());
        cargaHorariaRepository.saveAll(cargas);
    }

    public List<CargaHorariaConcluida> findCargaHorariaByUserId(User user) {
        return cargaHorariaRepository.findByUser(user);
    }

    public void calculateHours(HorarioAulaPratica horario, User user) {
        Integer horas = (int) Duration.between(horario.getHoraInicio(), horario.getHoraFim()).toHours();
        setHorasPratica(horas, user);
    }

    public void setHorasPratica(Integer horas, User user) {
        List<CargaHorariaConcluida> cargaHorariaList = cargaHorariaRepository.findByUser(user);
        for (CargaHorariaConcluida cargaHoraria : cargaHorariaList) {
            if (cargaHoraria.getAula().getId().equals(0L)) {
                cargaHoraria.setCargaHoraria(horas);
                cargaHorariaRepository.save(cargaHoraria);
            }
        }
    }

    public void setHours(int horas, User user, Aula aula){
        List<CargaHorariaConcluida> cargaHorariaList = cargaHorariaRepository.findByUser(user);
        for (CargaHorariaConcluida cargaHoraria : cargaHorariaList) {
            if(cargaHoraria.getAula().equals(aula)){
                cargaHoraria.setCargaHoraria(horas);
                cargaHorariaRepository.save(cargaHoraria);
            }
        }
    }
}
