package com.project.cnh_manager.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.models.Solicitacao;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.AulaRepository;
import com.project.cnh_manager.repositories.SolicitacaoRepository;
import com.project.cnh_manager.services.exceptions.AuthorizationException;

import jakarta.transaction.Transactional;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CargaHorariaService cargaHorariaService;

    public List<Solicitacao> getAllSolicitacoes() {
        return solicitacaoRepository.findAll().stream().toList();
    }

    public List<Solicitacao> getSolicitacoesByUser(UUID Id) {
        return solicitacaoRepository.findAll().stream()
                .filter(solicitacao -> solicitacao.getUser().getId().equals(Id))
                .collect(Collectors.toList());
    }

    public Solicitacao getById(UUID idSolicitacao) throws ObjectNotFoundException {
        return solicitacaoRepository.findById(idSolicitacao).orElseThrow(
                () -> new ObjectNotFoundException("Aluno não encontrada na base de dados", solicitacaoRepository));
    }

    @Transactional
    public Solicitacao create(Solicitacao solicitacao) {
        User user = UserService.authenticated();
        if (Objects.isNull(user)) {
            throw new AuthorizationException("Acesso negado!");
        }

        // String aulaId = solicitacao.getAulaId();
        // System.out.println(aulaId);
        Aula aula = aulaRepository.findById(Long.valueOf(solicitacao.getAulaId()))
                .orElseThrow(() -> new RuntimeException("Aula não encontrada com o ID fornecido"));

        User usuario = this.userService.findByLogin(user.getLogin());

        solicitacao.setId(null);
        solicitacao.setUser(usuario);

        solicitacao.setAula(aula);

        solicitacao = solicitacaoRepository.save(solicitacao);
        return solicitacao;
    }

    public Solicitacao atualizarSolicitacao(UUID id, Solicitacao solicitacaoAtualizada) {
        // Verifica se a solicitação com o ID fornecido existe no banco de dados
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada com o ID: " + id));

        if (solicitacaoAtualizada.isStatusAprovada()) {
            int horas = solicitacao.getHorasSolicitadas();
            cargaHorariaService.setHours(horas, solicitacao.getUser(), solicitacao.getAula());
        }

        // Atualiza os atributos da solicitação com base nos dados fornecidos
        solicitacao.setStatusAberta(solicitacaoAtualizada.isStatusAberta());
        solicitacao.setStatusAprovada(solicitacaoAtualizada.isStatusAprovada());
        // Continue atualizando outros campos conforme necessário

        return solicitacaoRepository.save(solicitacao);
    }
}
