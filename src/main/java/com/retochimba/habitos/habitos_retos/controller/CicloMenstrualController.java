package com.retochimba.habitos.habitos_retos.controller;

import com.retochimba.habitos.habitos_retos.dto.CicloMenstrualDTO;
import model.CicloMenstrual;
import com.retochimba.habitos.habitos_retos.service.CicloMenstrualService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.retochimba.com/ciclos")
@RequiredArgsConstructor
public class CicloMenstrualController {

    private final CicloMenstrualService service;

    @PostMapping
    public ResponseEntity<CicloMenstrual> registrar(@RequestBody CicloMenstrualDTO dto) {
        return ResponseEntity.ok(service.registrarCiclo(dto));
    }

    @GetMapping("/{emailUsuario}")
    public ResponseEntity<List<CicloMenstrual>> obtenerPorUsuario(@PathVariable String emailUsuario) {
        return ResponseEntity.ok(service.obtenerCiclosPorUsuario(emailUsuario));
    }
}