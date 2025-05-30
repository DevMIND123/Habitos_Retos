package com.retochimba.habitos.habitos_retos.controller;

import com.retochimba.habitos.habitos_retos.dto.EmbarazoRequestDTO;
import com.retochimba.habitos.habitos_retos.model.Embarazo;
import com.retochimba.habitos.habitos_retos.service.EmbarazoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/embarazo")
@RequiredArgsConstructor
public class EmbarazoController {

    private final EmbarazoService service;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody EmbarazoRequestDTO request) {
        service.registrarEmbarazo(request);
        return ResponseEntity.ok("Embarazo registrado correctamente.");
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<?> obtenerUltimoEmbarazo(@PathVariable String email) {
        Embarazo embarazo = service.obtenerUltimoEmbarazoPorEmail(email);
        if (embarazo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(embarazo);
    }
}