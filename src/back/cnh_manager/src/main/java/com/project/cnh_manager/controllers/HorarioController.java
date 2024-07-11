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
import org.springframework.web.bind.annotation.RestController;

import com.project.cnh_manager.dto.ResponseDTO;
import com.project.cnh_manager.models.HorarioAulaPratica;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.services.CargaHorariaConcluidaService;
import com.project.cnh_manager.services.HorarioPraticaService;
import com.project.cnh_manager.services.UserService;

import jakarta.validation.Valid;

@RestController

@RequestMapping("/horario")
public class HorarioController {

    @Autowired
    private HorarioPraticaService horarioService;

    @Autowired
    private CargaHorariaConcluidaService cargaHorariaConcluidaService;

    @Autowired
    private UserService userService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> postHorario(@RequestBody @Valid HorarioAulaPratica horario, Errors errors) {
        try {
            return new ResponseEntity<>(horarioService.create(horario), HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao cadastrar horario", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}/{userId}")
    public ResponseEntity<?> setUserInstrutor(@PathVariable UUID id, @PathVariable UUID userId, @RequestBody @Valid HorarioAulaPratica horario) {
        User aluno = userService.findById(userId);
        if (!cargaHorariaConcluidaService.verificaHorasPraticas(aluno)) {
            return ResponseEntity.ok(horarioService.setAdicional(horario, id, aluno));
        }
        HorarioAulaPratica horarioAtualizado = horarioService.setUserInstrutor(horario, id, aluno);
        return ResponseEntity.ok().body(horarioAtualizado);
    }

    @GetMapping("/all/user/{userId}")
    public ResponseEntity<List<HorarioAulaPratica>> getAllByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(horarioService.findAllByUserId(userId));
    }

    @GetMapping("/confirmed/{userId}")
    public ResponseEntity<List<HorarioAulaPratica>> getAllConfirmed(@PathVariable UUID userId){
        return ResponseEntity.ok(horarioService.findAllUserAwaitingApproval(userId));
    }

    @GetMapping("/all/{InstrutorId}")
    public ResponseEntity<List<HorarioAulaPratica>> getAllByInstrutorId(@PathVariable UUID id) {
        return ResponseEntity.ok(horarioService.findAllInstrutorById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<HorarioAulaPratica>> getAllHorarios() {
        return ResponseEntity.ok(horarioService.findAll());
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelHorario(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(horarioService.cancel(id));
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao cancelar horario", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHorario(@PathVariable UUID id) {
        try {
            horarioService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao deletar horario", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirmHorario(@PathVariable UUID id) {
        try {
            horarioService.confirm(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao confirmar horario", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/approveOrDeny/{horarioId}")
    public ResponseEntity<?> approveOrDenyHorario(@PathVariable UUID horarioId, @RequestBody @Valid HorarioAulaPratica horarioAtualizado) {
        try {
            horarioService.approveOrDeny(horarioId, horarioAtualizado);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Erro ao aprovar horario", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/awatingapproval/{instrutorId}")
    public ResponseEntity<List<HorarioAulaPratica>> findAwatingApprovalByInstrutor(@PathVariable UUID instrutorId) {
        return ResponseEntity.ok(horarioService.findAllAwaitingApprovalByInstrutorId(instrutorId));
    }
}