package com.project.cnh_manager.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.cnh_manager.models.CargaHorariaConcluida;
import com.project.cnh_manager.services.CargaHorariaConcluidaService;


@RestController
@RequestMapping("carga-horaria")
public class CargaHorariaConcluidaController {

    @Autowired
    CargaHorariaConcluidaService service;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CargaHorariaConcluida>> getCargaHorariaConcluida(@PathVariable UUID userId) {
        ArrayList<CargaHorariaConcluida> cargas = service.getCargasHorarias(userId);
        return new ResponseEntity<>(cargas, HttpStatus.OK);
    }


    // @GetMapping("/{cargaHorariaId}")
    // public ResponseEntity<?> getCargaHorariaConcluidaById(@PathVariable UUID id) {
    //     Optional<CargaHorariaConcluida> cargaHorariaConcluida = this.repository.findById(id);
    //     if (cargaHorariaConcluida.isPresent()) {
    //         return ResponseEntity.ok(cargaHorariaConcluida.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
}