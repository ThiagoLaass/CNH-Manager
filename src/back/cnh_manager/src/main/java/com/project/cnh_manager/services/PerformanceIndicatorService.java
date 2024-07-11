package com.project.cnh_manager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.Avaliacao;
import com.project.cnh_manager.models.Prova;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.models.UserRole;
import com.project.cnh_manager.repositories.AvaliacaoRepository;
import com.project.cnh_manager.repositories.ProvaRepository;
import com.project.cnh_manager.repositories.UserRepository;
import com.project.cnh_manager.services.exceptions.AuthorizationException;

import jakarta.transaction.Transactional;

@Service
public class PerformanceIndicatorService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProvaRepository provaRepository;

    // Métodos de avaliação
    public List<Avaliacao> getAllAvaliacoes() {
        return avaliacaoRepository.findAll().stream().toList();
    }

    public List<Avaliacao> getAvaliacoesByUser(UUID Id) {
        return avaliacaoRepository.findAll().stream()
                .filter(avaliacao -> avaliacao.getUser().getId().equals(Id))
                .collect(Collectors.toList());
    }

    public Avaliacao getById(UUID idAvaliacao) throws ObjectNotFoundException {
        return avaliacaoRepository.findById(idAvaliacao).orElseThrow(
                () -> new ObjectNotFoundException("Aluno não encontrada na base de dados", avaliacaoRepository));
    }

    @Transactional
    public Avaliacao create(Avaliacao avaliacao) {

        User user = UserService.authenticated();
        if (Objects.isNull(user)) {
            throw new AuthorizationException("Acesso negado!");
        }

        User usuario = this.userService.findByLogin(user.getLogin());

        avaliacao.setId(null);
        avaliacao.setUser(usuario);

        avaliacao = avaliacaoRepository.save(avaliacao);
        return avaliacao;
    }

    public double getAverageAvaliacoes(){
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();
        double total = avaliacoes.size();
        double soma = 0;
        for (Avaliacao avaliacao : avaliacoes) {
            soma += avaliacao.getNumAvaliacao();
        }
        return soma / total;
    }
    // Término dos métodos de avaliação



    // Método da media de alunos aprovados
    public Double calculateApprovalRate() {
        List<User> users = userRepository.findAll();
        
        // Filtra os usuários que são do tipo UserRole.USER
        List<User> filteredUsers = users.stream()
                .filter(user -> user.getRole() == UserRole.USER)
                .collect(Collectors.toList());
        
        double total = filteredUsers.size();
    
        if (total == 0) {
            return 0.0;
        }
    
        double approved = filteredUsers.stream()
                .filter(User::isCnhAprovada)
                .count();
    
        return approved / total * 100;
    }
    // Término do método da media de alunos aprovados

    public Float getMediaAulasPraticasAdicionaisNecessarias(){

        try {
            int quantUsuarios = 0;
            int aulasAdicionaisTotal = 0;
            List<User> usuariosConsultados = userRepository.findAll().stream().toList();
            List<User> usuarios = new ArrayList<>();
            for (User user : usuariosConsultados) {
                if (user.isCnhAprovada()){
                    usuarios.add(user);
                }
            }

            for (User usuario : usuarios){
                if(usuario.getQuantAulasAdicionais() != 0){
                    aulasAdicionaisTotal += usuario.getQuantAulasAdicionais();
                }
                quantUsuarios++;
            }

            float mediaAulasAdicionaisNecessarias = aulasAdicionaisTotal/quantUsuarios;

            return mediaAulasAdicionaisNecessarias;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular a média de aulas práticas adicionais necessárias.", e);
        }
        
    }

    // public Float getMediaAulasPraticasAdicionaisNecessarias() {

    //     try {
    //         int quantUsuarios = 0;
    //         int aulasAdicionaisTotal = 0;
    //         List<User> usuarios = userService.findAll();

    //         for (User usuario : usuarios) {
    //             if (usuario.getRole() == UserRole.USER && usuario.getQuantAulasAdicionais() != 0) {
    //                 aulasAdicionaisTotal += usuario.getQuantAulasAdicionais();
    //                 quantUsuarios++;
    //             }
    //         }

    //         if (quantUsuarios == 0) {
    //             return 0.0f;
    //         }

    //         float mediaAulasAdicionaisNecessarias = (float) aulasAdicionaisTotal / quantUsuarios;

    //         return mediaAulasAdicionaisNecessarias;
    //     } catch (Exception e) {
    //         throw new RuntimeException("Erro ao calcular a média de aulas práticas adicionais necessárias.", e);
    //     }
    // }


    // Método de quantidade de aulas praticas feitas pelo aluno

    
}