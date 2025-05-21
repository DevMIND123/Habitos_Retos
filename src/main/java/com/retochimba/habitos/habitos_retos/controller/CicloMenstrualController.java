package com.retochimba.habitos.habitos_retos.controller;

import com.retochimba.habitos.habitos_retos.dto.CicloMenstrualDTO;
import com.retochimba.habitos.habitos_retos.security.JwtUtils;

import com.retochimba.habitos.habitos_retos.model.CicloMenstrual;
import com.retochimba.habitos.habitos_retos.service.CicloMenstrualService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.retochimba.com/ciclos")
@RequiredArgsConstructor
public class CicloMenstrualController {

    private final CicloMenstrualService service;

    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping
    public ResponseEntity<CicloMenstrual> registrarCiclo(
            @RequestBody CicloMenstrualDTO dto,
            @RequestHeader("Authorization") String token) {

        String emailUsuario = jwtUtils.extraerEmail(token);
        dto.setEmailUsuario(emailUsuario);

        return ResponseEntity.ok(service.registrarCiclo(dto));
    }

    @GetMapping("/{emailUsuario}")
    public ResponseEntity<List<CicloMenstrual>> obtenerPorUsuario(@PathVariable String emailUsuario) {
        return ResponseEntity.ok(service.obtenerCiclosPorUsuario(emailUsuario));
    }
}