package com.project.cnh_manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.models.CargaHorariaConcluida;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.AulaRepository;
import com.project.cnh_manager.services.CargaHorariaConcluidaService;

public class CargaHorariaConcluidaServiceTest {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private CargaHorariaConcluidaService cargaHorariaConcluidaService;


    @Test
    public void testVerificaHorasPraticasComCargaHorariaMenor() {
        UUID randomId = UUID.randomUUID();
        User aluno = new User();
        aluno.setId(randomId);
        Aula aula = new Aula(10L, "Prática", 20, null, null);
        CargaHorariaConcluida cargaHoraria = new CargaHorariaConcluida(5, null, aula); 

        List<Aula> aulas = new ArrayList<>();
        aulas.add(aula);
        when(aulaRepository.findAll()).thenReturn(aulas);

        ArrayList<CargaHorariaConcluida> cargasHorarias = new ArrayList<>();
        cargasHorarias.add(cargaHoraria);
        when(cargaHorariaConcluidaService.getCargasHorarias(randomId)).thenReturn(cargasHorarias);

        boolean resultado = cargaHorariaConcluidaService.verificaHorasPraticas(aluno);

        assertEquals(false, resultado);
    }

    @Test
    public void testVerificaHorasPraticasComCargaHorariaMaior() {
        UUID randomId = UUID.randomUUID();
        User aluno = new User();
        aluno.setId(randomId);
        Aula aula = new Aula(10L, "Prática", 20, null, null);
        CargaHorariaConcluida cargaHoraria = new CargaHorariaConcluida(25, null, aula); 

        List<Aula> aulas = new ArrayList<>();
        aulas.add(aula);
        when(aulaRepository.findAll()).thenReturn(aulas);

        ArrayList<CargaHorariaConcluida> cargasHorarias = new ArrayList<>();
        cargasHorarias.add(cargaHoraria);
        when(cargaHorariaConcluidaService.getCargasHorarias(randomId)).thenReturn(cargasHorarias);

        boolean resultado = cargaHorariaConcluidaService.verificaHorasPraticas(aluno);

        assertEquals(true, resultado);
    }

    @Test
    public void testVerificaHorasPraticasComCargaHorariaIgual() {
        UUID randomId = UUID.randomUUID();
        User aluno = new User();
        aluno.setId(randomId);
        Aula aula = new Aula(10L, "Prática", 20, null, null);
        CargaHorariaConcluida cargaHoraria = new CargaHorariaConcluida(20, null, aula); 

        List<Aula> aulas = new ArrayList<>();
        aulas.add(aula);
        when(aulaRepository.findAll()).thenReturn(aulas);

        ArrayList<CargaHorariaConcluida> cargasHorarias = new ArrayList<>();
        cargasHorarias.add(cargaHoraria);
        when(cargaHorariaConcluidaService.getCargasHorarias(randomId)).thenReturn(cargasHorarias);

        boolean resultado = cargaHorariaConcluidaService.verificaHorasPraticas(aluno);

        assertEquals(true, resultado);
    }

    @Test
    public void setHorasPratica(){
        UUID randomId = UUID.randomUUID();
        User aluno = new User();
        aluno.setId(randomId);
        Aula aula = new Aula(10L, "Prática", 20, null, null);
        CargaHorariaConcluida cargaHoraria = new CargaHorariaConcluida(20, null, aula); 

        List<Aula> aulas = new ArrayList<>();
        aulas.add(aula);
        when(aulaRepository.findAll()).thenReturn(aulas);

        ArrayList<CargaHorariaConcluida> cargasHorarias = new ArrayList<>();
        cargasHorarias.add(cargaHoraria);
        when(cargaHorariaConcluidaService.getCargasHorarias(randomId)).thenReturn(cargasHorarias);

        cargaHorariaConcluidaService.setHorasPratica(aluno, 30);

        assertEquals(30, cargaHoraria.getCargaHoraria());
    }

    @Test
    public void testGetCargasHorarias(){
        UUID randomId = UUID.randomUUID();
        User aluno = new User();
        aluno.setId(randomId);
        Aula aula = new Aula(10L, "Prática", 20, null, null);
        CargaHorariaConcluida cargaHoraria = new CargaHorariaConcluida(20, null, aula); 

        List<Aula> aulas = new ArrayList<>();
        aulas.add(aula);
        when(aulaRepository.findAll()).thenReturn(aulas);

        ArrayList<CargaHorariaConcluida> cargasHorarias = new ArrayList<>();
        cargasHorarias.add(cargaHoraria);
        when(cargaHorariaConcluidaService.getCargasHorarias(randomId)).thenReturn(cargasHorarias);

        List<CargaHorariaConcluida> resultado = cargaHorariaConcluidaService.getCargasHorarias(randomId);

        assertEquals(1, resultado.size());
        assertEquals(20, resultado.get(0).getCargaHoraria());
    }

}