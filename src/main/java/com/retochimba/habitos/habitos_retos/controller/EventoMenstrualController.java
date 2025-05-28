package com.retochimba.habitos.habitos_retos.controller;

import com.retochimba.habitos.habitos_retos.dto.EventoMenstrualDTO;
import com.retochimba.habitos.habitos_retos.service.EventoMenstrualService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api.retochimba.com/evento-menstrual")
@RequiredArgsConstructor
public class EventoMenstrualController {

    private final EventoMenstrualService service;

    @PostMapping("/registrar")
    public ResponseEntity<EventoMenstrualDTO> registrar(@RequestBody EventoMenstrualDTO dto) {
        return ResponseEntity.ok(service.registrarEvento(dto));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<EventoMenstrualDTO>> porUsuario(@PathVariable String email) {
        return ResponseEntity.ok(service.obtenerEventosPorUsuario(email));
    }

    @GetMapping("/rango/{email}")
    public ResponseEntity<List<EventoMenstrualDTO>> porRango(
            @PathVariable String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    ) {
        return ResponseEntity.ok(service.obtenerEventosPorRango(email, inicio, fin));
    }
}
