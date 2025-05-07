package com.retochimba.habitos.habitos_retos.controller;

import com.retochimba.habitos.habitos_retos.dto.SintomaMenstrualDTO;
import com.retochimba.habitos.habitos_retos.service.SintomaMenstrualService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sintoma-menstrual")
@RequiredArgsConstructor
public class SintomaMenstrualController {

    private final SintomaMenstrualService service;

    @PostMapping("/registrar")
    public ResponseEntity<SintomaMenstrualDTO> registrar(@RequestBody SintomaMenstrualDTO dto) {
        return ResponseEntity.ok(service.registrarSintoma(dto));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<SintomaMenstrualDTO>> porUsuario(@PathVariable String email) {
        return ResponseEntity.ok(service.obtenerSintomasPorUsuario(email));
    }

    @GetMapping("/rango/{email}")
    public ResponseEntity<List<SintomaMenstrualDTO>> porRango(
            @PathVariable String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    ) {
        return ResponseEntity.ok(service.obtenerSintomasPorRango(email, inicio, fin));
    }
}
