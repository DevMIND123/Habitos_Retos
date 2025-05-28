package com.retochimba.habitos.habitos_retos.controller;

import com.retochimba.habitos.habitos_retos.dto.RegistroComidaDTO;
import com.retochimba.habitos.habitos_retos.service.RegistroComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.retochimba.com/comida")
@RequiredArgsConstructor
public class RegistroComidaController {

    private final RegistroComidaService comidaService;

    @PostMapping("/registrar/{alimentacionId}")
    public ResponseEntity<RegistroComidaDTO> registrar(
            @PathVariable Long alimentacionId,
            @RequestBody RegistroComidaDTO dto) {
        RegistroComidaDTO registrada = comidaService.registrarComida(alimentacionId, dto);
        return ResponseEntity.ok(registrada);
    }

    @GetMapping("/alimentacion/{alimentacionId}")
    public ResponseEntity<List<RegistroComidaDTO>> obtenerPorAlimentacion(@PathVariable Long alimentacionId) {
        return ResponseEntity.ok(comidaService.obtenerComidasPorAlimentacion(alimentacionId));
    }
}
