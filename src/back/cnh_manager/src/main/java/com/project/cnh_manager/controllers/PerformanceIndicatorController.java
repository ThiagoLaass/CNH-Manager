package com.project.cnh_manager.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cnh_manager.dto.ResponseDTO;
import com.project.cnh_manager.models.Avaliacao;
import com.project.cnh_manager.services.PerformanceIndicatorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("indicators")
public class PerformanceIndicatorController {


    @Autowired
    PerformanceIndicatorService performanceIndicatorService;
    

    // Avaliações
    @GetMapping("/avaliacoes/all")
    public ResponseEntity<List<Avaliacao>> getAllAvaliacoes(){
        return ResponseEntity.ok(performanceIndicatorService.getAllAvaliacoes());
    }

     @GetMapping("/avaliacoes/all/{id}")
    public ResponseEntity<List<Avaliacao>> getAllAvaliacoesOfUser(@PathVariable UUID id) {
        return ResponseEntity.ok(performanceIndicatorService.getAvaliacoesByUser(id));
    }

    @GetMapping("/avaliacoes/average")
    public ResponseEntity<?> getAverageAvaliacoes() {
        return ResponseEntity.ok(performanceIndicatorService.getAverageAvaliacoes());
    }
    
     @GetMapping("/avaliacoes/{id}")
    public ResponseEntity<Avaliacao> getAvaliacaoById(@PathVariable UUID id) {
        Avaliacao avaliacao = performanceIndicatorService.getById(id);
        return new ResponseEntity<>(avaliacao, HttpStatus.OK);
    }

    @PostMapping("/avaliacoes/cadastrar")
    @ResponseBody
    public ResponseEntity<?> postAvaliacao(@RequestBody @Valid Avaliacao avaliacao, Errors errors) {
        try {
            Avaliacao createAvaliacao = performanceIndicatorService.create(avaliacao);
            //return ResponseEntity.ok(createdSolicitacao);
            return new ResponseEntity<>(createAvaliacao, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao cadastrar solicitação de presença", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    // Término dos métodos de avaliações

    
    // Média de aprovação
    @GetMapping("/calculateApprovalRate")
    public ResponseEntity<?> calculateApprovalRate() {
        return ResponseEntity.ok(performanceIndicatorService.calculateApprovalRate());
    }
    // Término dos métodos da média de aprovação

    // Calcular a quantidade média de aulas práticas adicionais que os alunos precisam para serem aprovado na prova prática
    @GetMapping("/calculateAverageAdditionalPractice")
    public ResponseEntity<Float> getMediaAulasPraticasAdicionaisNecessarias(){
        try {
            Float media = performanceIndicatorService.getMediaAulasPraticasAdicionaisNecessarias();
            return new ResponseEntity<>(media, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}