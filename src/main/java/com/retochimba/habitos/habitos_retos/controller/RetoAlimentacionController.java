package com.retochimba.habitos.habitos_retos.controller;

import com.retochimba.habitos.habitos_retos.dto.RetoAlimentacionDTO;
import com.retochimba.habitos.habitos_retos.service.RetoAlimentacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reto")
@RequiredArgsConstructor
public class RetoAlimentacionController {

    private final RetoAlimentacionService retoService;

    @PostMapping("/asignar/{alimentacionId}")
    public ResponseEntity<RetoAlimentacionDTO> asignar(@PathVariable Long alimentacionId,
                                                       @RequestBody RetoAlimentacionDTO dto) {
        return ResponseEntity.ok(retoService.asignarReto(alimentacionId, dto));
    }

    @GetMapping("/alimentacion/{alimentacionId}")
    public ResponseEntity<List<RetoAlimentacionDTO>> obtenerPorAlimentacion(@PathVariable Long alimentacionId) {
        return ResponseEntity.ok(retoService.obtenerRetosPorAlimentacion(alimentacionId));
    }

    @PatchMapping("/completar/{retoId}")
    public ResponseEntity<RetoAlimentacionDTO> completar(@PathVariable Long retoId) {
        return ResponseEntity.ok(retoService.completarReto(retoId));
    }
}
