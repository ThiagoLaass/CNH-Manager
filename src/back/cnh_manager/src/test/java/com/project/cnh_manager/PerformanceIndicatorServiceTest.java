package com.project.cnh_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cnh_manager.models.Avaliacao;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.AvaliacaoRepository;
import com.project.cnh_manager.services.PerformanceIndicatorService;

@SpringBootTest
public class PerformanceIndicatorServiceTest {

    PerformanceIndicatorService performanceIndicatorService;

    AvaliacaoRepository avaliacaoRepository;
        
    @Test
    public void testGetAverageAvaliacoes(){
        
        Avaliacao a1 = new Avaliacao();
        Avaliacao a2 = new Avaliacao();
        Avaliacao a3 = new Avaliacao();

        a1.setNumAvaliacao(3);
        a2.setNumAvaliacao(2);
        a3.setNumAvaliacao(5);

        performanceIndicatorService.getAverageAvaliacoes();

        assertEquals(3.33, performanceIndicatorService.getAverageAvaliacoes());
    }

    @Test
    public void testCreate(){
        Avaliacao a = new Avaliacao();
        UUID avaliacaoId = UUID.randomUUID();
        a.setId(avaliacaoId);
        a.setNumAvaliacao(3);
        performanceIndicatorService.create(a);
        assertEquals(a, avaliacaoRepository.findById(avaliacaoId));
    }

    @Test 
    public void testCalculateApprovalRate(){
        User aluno1 = new User();
        User aluno2 = new User();
        User aluno3 = new User();
        User aluno4 = new User();

        aluno1.setCnhAprovada(false);
        aluno2.setCnhAprovada(true);
        aluno3.setCnhAprovada(true);
        aluno4.setCnhAprovada(false);

        assertEquals(50, performanceIndicatorService.calculateApprovalRate());
    }
}