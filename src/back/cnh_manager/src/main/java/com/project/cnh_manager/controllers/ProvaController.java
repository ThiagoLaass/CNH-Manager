package com.project.cnh_manager.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cnh_manager.dto.ResponseDTO;
import com.project.cnh_manager.models.Prova;
import com.project.cnh_manager.services.ProvaService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("provas")
public class ProvaController {

    @Autowired
    private ProvaService provaService;

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> putProva(@PathVariable UUID id, @RequestBody @Valid Prova prova, Errors errors) {
        try {
            Prova provaAtualizada = provaService.atualizaProva(id, prova);
            return ResponseEntity.ok().body(provaAtualizada);
            
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao cadastrar prova", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } 
    }

    // @PutMapping("/aprovarAluno/{id}")
    // @ResponseBody
    // public ResponseEntity<?> aprovarProva(@PathVariable UUID id, @RequestBody @Valid Prova prova, Errors errors) {
    //     try {
    //         Prova provaAtualizada = provaService.aprovaProva(prova);
    //         return ResponseEntity.ok().body(provaAtualizada);

    //     } catch (Exception e) {
    //         ResponseDTO errorResponse = new ResponseDTO("Erro ao cadastrar prova", e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    //     } 
    // }
    

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Prova>> getAllProvasOfUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(provaService.getProvasByUser(userId));
    }

    // topdas as provas que não foram concluías
    @GetMapping("/inconcluidas")
    public ResponseEntity<List<Prova>> getProvasNaoConcluidas(){
        try {
            return ResponseEntity.ok(provaService.getProvasNaoConcluidas());
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
       // return ResponseEntity.ok(solicitacaoService.getAllSolicitacoes());
    }

    
    
    
}
