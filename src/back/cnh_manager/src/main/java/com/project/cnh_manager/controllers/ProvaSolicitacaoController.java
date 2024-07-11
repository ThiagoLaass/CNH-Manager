package com.project.cnh_manager.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cnh_manager.dto.ResponseDTO;
import com.project.cnh_manager.models.ProvaSolicitacao;
import com.project.cnh_manager.services.ProvaSolicitacaoService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("solicitacaoDeProva")
public class ProvaSolicitacaoController {

    @Autowired
    private ProvaSolicitacaoService provaSolicitacaoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> postProvaSolicitacao(@RequestBody @Valid ProvaSolicitacao solicitacaoDeProva, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        try {
            ProvaSolicitacao createdSolicitacao = provaSolicitacaoService.verificaAptidaoParaSolicitarProva(solicitacaoDeProva);
            return new ResponseEntity<>(createdSolicitacao, HttpStatus.CREATED);

        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao cadastrar solicitação de prova", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/all")
        public ResponseEntity<List<ProvaSolicitacao>> getAllSolicitacoesDeProva(){
        return ResponseEntity.ok(provaSolicitacaoService.getAllSolicitacoesDeProva());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<ProvaSolicitacao>> getAllSolicitacoesDeProvaByUser(@PathVariable UUID id){
    return ResponseEntity.ok(provaSolicitacaoService.findAllByUserId(id));
}


    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ProvaSolicitacao> putProvaSolicitacao(@PathVariable UUID id, @RequestBody ProvaSolicitacao solicitacaoDeProva) {
        ProvaSolicitacao solicitacaoAtualizada = provaSolicitacaoService.atualizaSolicitacao(id, solicitacaoDeProva);
        return ResponseEntity.ok().body(solicitacaoAtualizada);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteProvaSolicitacao(@PathVariable UUID id) {
        provaSolicitacaoService.delete(id);
        ResponseDTO responseDTO = new ResponseDTO("Solicitação de prova deletada com sucesso",
                "A solicitação de prova foi deletada do sistema");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    

}