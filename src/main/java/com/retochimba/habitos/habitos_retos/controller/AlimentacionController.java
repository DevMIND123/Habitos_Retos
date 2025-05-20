package com.retochimba.habitos.habitos_retos.controller;

import com.retochimba.habitos.habitos_retos.dto.AlimentacionDTO;
import com.retochimba.habitos.habitos_retos.service.AlimentacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.retochimba.com/alimentacion")
@RequiredArgsConstructor
public class AlimentacionController {

    private final AlimentacionService alimentacionService;

    @PostMapping("/crear")
    public ResponseEntity<AlimentacionDTO> crear(@RequestBody AlimentacionDTO dto) {
        AlimentacionDTO creado = alimentacionService.crearAlimentacion(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/por-email/{email}")
    public ResponseEntity<List<AlimentacionDTO>> obtenerPorEmail(@PathVariable String email) {
        List<AlimentacionDTO> lista = alimentacionService.obtenerTodosPorEmail(email);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

}
