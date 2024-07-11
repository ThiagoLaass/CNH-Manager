package com.project.cnh_manager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.Prova;
import com.project.cnh_manager.models.ProvaSolicitacao;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.ProvaRepository;
import com.project.cnh_manager.repositories.ProvaSolicitacaoRepository;
import com.project.cnh_manager.repositories.UserRepository;

@Service
public class ProvaService {

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private ProvaSolicitacaoRepository provaSolicitacaoRepository;


    @Autowired
    private UserRepository userRepository;

    public Prova atualizaProva(UUID id, Prova novaProva) {
        Prova provaAtualizada = provaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada com o ID: " + id));

        atualizarCamposProva(provaAtualizada, novaProva);

        if (novaProva.isStatusAprovado()) {
            aprovaProva(provaAtualizada);
        } else {
            reprovaProva(provaAtualizada);
        }
        return provaRepository.save(provaAtualizada);
    }


    private void reprovaProva(Prova provaAtualizada) {
        User user = provaAtualizada.getUser();
        if (user != null) {
            user.setCnhAprovada(false);
            deletarProvaSolicitacaoSeExistir(user);
        }
        provaAtualizada.setStatusAprovado(false);
    }

    private void aprovaProva(Prova provaAtualizada) {
        User user = provaAtualizada.getUser();
        if (user != null) {
            deletarProvaSolicitacaoSeExistir(user);
            aprovarCnhSeProvaPratica(provaAtualizada, user);
        }
        provaAtualizada.setStatusAprovado(true);
    }

    private void atualizarCamposProva(Prova provaAtualizada, Prova novaProva) {
        if (novaProva.getData() != null) {
            provaAtualizada.setData(novaProva.getData());
        }
        if (novaProva.getHorario() != null) {
            provaAtualizada.setHorario(novaProva.getHorario());
        }
    }

    private void deletarProvaSolicitacaoSeExistir(User user) {
        ProvaSolicitacao solicitacao = provaSolicitacaoRepository.findByUserId(user.getId());
        if (solicitacao != null) {
            provaSolicitacaoRepository.delete(solicitacao);
        }
    }

    private void aprovarCnhSeProvaPratica(Prova provaAtualizada, User user) {
        if ("Prática".equals(provaAtualizada.getTipoProva().getNome())) {
            user.setCnhAprovada(true);
        }
    }

    public List<Prova> getProvasByUser(UUID userId) {
        return provaRepository.findAll().stream()
                .filter(solicitacao -> solicitacao.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Prova> getProvasNaoConcluidas() {
        List<Prova> provas = provaRepository.findAll().stream().toList();
        List<Prova> provasNaoConcluidas = new ArrayList<>();

        for (Prova prova : provas) {
            if (!prova.isStatusAprovado()) {
                provasNaoConcluidas.add(prova);
            }
        }

        return provasNaoConcluidas;
    }
}
