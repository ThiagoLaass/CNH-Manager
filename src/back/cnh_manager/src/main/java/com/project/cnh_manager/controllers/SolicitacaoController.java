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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cnh_manager.dto.ResponseDTO;
import com.project.cnh_manager.models.Solicitacao;
import com.project.cnh_manager.services.SolicitacaoService;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("solicitacoes")
public class SolicitacaoController {

    @Autowired
    SolicitacaoService solicitacaoService;

    @GetMapping("/all")
    public ResponseEntity<List<Solicitacao>> getAllSolicitacoes(){
        return ResponseEntity.ok(solicitacaoService.getAllSolicitacoes());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Solicitacao>> getAllSolicitacoesOfUser(@PathVariable UUID id) {
        return ResponseEntity.ok(solicitacaoService.getSolicitacoesByUser(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> getSolicitacaoById(@PathVariable UUID id) {
        Solicitacao solicitacao = solicitacaoService.getById(id);
        return new ResponseEntity<>(solicitacao, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    @ResponseBody
    public ResponseEntity<?> postSolicitacao(@RequestBody @Valid Solicitacao solicitacao, Errors errors) {
        try {
            Solicitacao createdSolicitacao = solicitacaoService.create(solicitacao);
            //return ResponseEntity.ok(createdSolicitacao);
            return new ResponseEntity<>(createdSolicitacao, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao cadastrar solicitação de presença", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Solicitacao> atualizarSolicitacao(@PathVariable UUID id, @RequestBody Solicitacao solicitacao) {
        Solicitacao solicitacaoAtualizada = solicitacaoService.atualizarSolicitacao(id, solicitacao);
        return ResponseEntity.ok().body(solicitacaoAtualizada);
    }
}