package com.project.cnh_manager.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cnh_manager.models.Pagamento;
import com.project.cnh_manager.services.PagamentoService;




@RestController()
@RequestMapping("pagamentos")
public class PagamentoController {

    @Autowired 
    private PagamentoService pagamentoService;

    @GetMapping("/all")
    public ResponseEntity<List<Pagamento>> getAllPagamento(){
        return ResponseEntity.ok(pagamentoService.getAllPagamento());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Pagamento>> getAllPagamentoById(@PathVariable UUID id){
        return ResponseEntity.ok(pagamentoService.getAllById(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> postPagamento(@RequestBody Pagamento pagamento) {
        try {
            return ResponseEntity.ok(pagamentoService.createPagamentoeTipoPagamento(pagamento.getUser()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public UUID putPagamentoAprovado(@PathVariable UUID id, @RequestBody Pagamento pagamento) {
        Pagamento pagamentoAtualizado = pagamentoService.atualizarPagamento(id, pagamento);
        return pagamentoAtualizado.getId();
    }

    // Update e Delete pagamento
    // quando o statusPagemento for atualizado pra True, cria automaticamente uma prova

}