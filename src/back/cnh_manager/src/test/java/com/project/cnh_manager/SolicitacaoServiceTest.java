package com.project.cnh_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.models.Solicitacao;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.models.UserRole;
import com.project.cnh_manager.repositories.AulaRepository;
import com.project.cnh_manager.repositories.SolicitacaoRepository;
import com.project.cnh_manager.repositories.UserRepository;
import com.project.cnh_manager.services.SolicitacaoService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SolicitacaoServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private SolicitacaoService solicitacaoService;

    @Test
    public void testGetSolicitacoesByUser() {

        User user = new User("userlogin", "user@email.com", "senha123", UserRole.USER);
        userRepository.save(user);

        Aula aula = new Aula();
        aula.setNome("Meio ambiente 2");
        aula.setCargaHoraria(40);
        aulaRepository.save(aula); 

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setDescricao("Confirme minha presenca por favor.");
        solicitacao.setHorasSolicitadas(10); 
        solicitacao.setStatusAberta(true); 
        solicitacao.setStatusAprovada(false);
        solicitacao.setUser(user); 
        solicitacao.setAula(aula); 

        solicitacaoRepository.save(solicitacao);


        List<Solicitacao> solicitacoes = solicitacaoService.getSolicitacoesByUser(user.getId());

        assertEquals(1, solicitacoes.size());
        assertEquals(user.getId(), solicitacoes.get(0).getUser().getId());
    }

    @Test
    @Transactional
    public void testAtualizarSolicitacao() {

        User user = new User("userlogin2", "user2@email.com", "senha123", UserRole.USER);
        userRepository.save(user);

        Aula aula = new Aula();
        aula.setNome("Meio ambiente 3");
        aula.setCargaHoraria(40);
        aulaRepository.save(aula); 

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setDescricao("Desejo confirmar minha presenca nessa aula");
        solicitacao.setHorasSolicitadas(10); 
        solicitacao.setStatusAberta(true); 
        solicitacao.setStatusAprovada(false);
        solicitacao.setUser(user); 
        solicitacao.setAula(aula); 

        solicitacaoRepository.save(solicitacao);

        solicitacao.setStatusAberta(false);
        solicitacao.setStatusAprovada(true);
        solicitacao.setHorasSolicitadas(5);

        // Teste do método atualizarSolicitacao
        Solicitacao result = solicitacaoService.atualizarSolicitacao(solicitacao.getId(), solicitacao);

        assertEquals(false, result.isStatusAberta());
        assertEquals(true, result.isStatusAprovada());
    }

    @Test
    @Transactional
    public void testAtualizarSolicitacaoNaoEncontrada() {
        // Teste de objeto não encontrado no método atualizarSolicitacao
        UUID solicitacaoId = UUID.randomUUID();

        Solicitacao solicitacaoAtualizada = new Solicitacao();

        assertThrows(RuntimeException.class, () -> solicitacaoService.atualizarSolicitacao(solicitacaoId, solicitacaoAtualizada));
    }
}
    
