package com.project.cnh_manager.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.HorarioAulaPratica;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.HorarioAulaPraticaRepository;
import com.project.cnh_manager.repositories.UserRepository;

@Service
public class HorarioPraticaService {

    @Autowired
    private UserService userService;

    @Autowired
    private HorarioAulaPraticaRepository horarioAulaPraticaRepository;

    @Autowired
    private CargaHorariaService cargaHorariaService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PagamentoService pagamentoService;

    public List<HorarioAulaPratica> findAllByUserId(UUID id) {
        return horarioAulaPraticaRepository.findAll().stream()
                .filter(horario -> horario.getAluno() != null && horario.getAluno().getId().equals(id))
                .collect(Collectors.toList());
    }

    public List<HorarioAulaPratica> findAllInstrutorById(UUID id) {
        return horarioAulaPraticaRepository.findAll().stream()
                .filter(horario -> horario.getInstrutor().getId().equals(id))
                .collect(Collectors.toList());
    }

    public List<HorarioAulaPratica> findAll() {
        return horarioAulaPraticaRepository.findAll().stream().toList();
    }

    public HorarioAulaPratica create(HorarioAulaPratica horario) {
        User user = UserService.authenticated();
        User usuario = userService.findByLogin(user.getLogin());

        horario.setAluno(null);
        horario.setInstrutor(usuario);
        horario.setStatusAberto(true);

        horario = horarioAulaPraticaRepository.save(horario);
        return horario;
    }

    public HorarioAulaPratica setUserInstrutor(HorarioAulaPratica horarioAtualizado, UUID id, User aluno) {
        HorarioAulaPratica horario = horarioAulaPraticaRepository.findById(id).orElseThrow();

        horario.setAluno(aluno);

        horario.setStatusAberto(horarioAtualizado.isStatusAberto());

        return horarioAulaPraticaRepository.save(horario);
    }

    public HorarioAulaPratica cancel(UUID id) {
        HorarioAulaPratica horario = horarioAulaPraticaRepository.findById(id).orElseThrow();
        horario.setStatusAberto(true);
        horario.setAluno(null);
        return horarioAulaPraticaRepository.save(horario);
    }

    public void delete(UUID id) {
        horarioAulaPraticaRepository.deleteById(id);
    }

    public void confirm(UUID id) {
        HorarioAulaPratica horario = horarioAulaPraticaRepository.findById(id).orElseThrow();
        horario.setAguardandoAprovacao(true);
        horarioAulaPraticaRepository.save(horario);
    }

    public HorarioAulaPratica setAdicional(HorarioAulaPratica horario, UUID horarioId, User user) {
        HorarioAulaPratica horarioAtualizado = horarioAulaPraticaRepository.findById(horarioId).orElseThrow();
        horarioAtualizado.setAluno(user);
        horarioAtualizado.setStatusAberto(false);
        horarioAtualizado.setStatusAprovado(false);
        horarioAtualizado.setAulaAdicional(true);
        horarioAtualizado.setPagamento(pagamentoService.createPagamentoeTipoPagamento(user));
        return horarioAulaPraticaRepository.save(horarioAtualizado);
    }

    public HorarioAulaPratica approveOrDeny(UUID horarioId, HorarioAulaPratica horarioAtualizado) {
        HorarioAulaPratica horario = horarioAulaPraticaRepository.findById(horarioId).orElseThrow();
        User aluno =  horario.getAluno();
        if(horarioAtualizado.isStatusAprovado()){
        horario.setStatusAprovado(horarioAtualizado.isStatusAprovado());
        horario.setStatusAberto(true);
        horario.setAguardandoAprovacao(false);
        horario.setAluno(null);
        cargaHorariaService.calculateHours(horario, aluno);
        pagamentoService.approvePagamento(horario);
        horario.setPagamento(null);

        int aulasAdicionais = aluno.getQuantAulasAdicionais();
        aulasAdicionais += 1;
        aluno.setQuantAulasAdicionais(aulasAdicionais);
        userRepository.save(aluno);
        return horarioAulaPraticaRepository.save(horario);
    }
        else {
            horario.setStatusAprovado(false);
            horario.setStatusAberto(true);
            horario.setAguardandoAprovacao(false);
            horario.setAluno(null);
            pagamentoService.denyPagamento(horario);
            horario.setPagamento(null);
            return horarioAulaPraticaRepository.save(horario);
        }
    }

    public List<HorarioAulaPratica> findAllAwaitingApprovalByInstrutorId(UUID id) {
        return horarioAulaPraticaRepository.findAll().stream()
                .filter(horario -> horario.isAguardandoAprovacao() && horario.getInstrutor().getId().equals(id))
                .collect(Collectors.toList());
    }

    public List<HorarioAulaPratica> findAllUserAwaitingApproval(UUID userId) {
        return horarioAulaPraticaRepository.findAll().stream()
                .filter(horario -> horario.isAguardandoAprovacao() && horario.getAluno().getId().equals(userId))
                .collect(Collectors.toList());
    }
}   