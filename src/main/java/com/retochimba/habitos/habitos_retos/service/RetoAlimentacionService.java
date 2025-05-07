package com.retochimba.habitos.habitos_retos.service;

import com.retochimba.habitos.habitos_retos.dto.RetoAlimentacionDTO;

import java.util.List;

public interface RetoAlimentacionService {
    RetoAlimentacionDTO asignarReto(Long alimentacionId, RetoAlimentacionDTO dto);
    List<RetoAlimentacionDTO> obtenerRetosPorAlimentacion(Long alimentacionId);
    RetoAlimentacionDTO completarReto(Long retoId);
}
