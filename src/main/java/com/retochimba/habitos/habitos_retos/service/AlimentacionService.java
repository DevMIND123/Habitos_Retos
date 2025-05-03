package com.retochimba.habitos.habitos_retos.service;

import com.retochimba.habitos.habitos_retos.dto.AlimentacionDTO;

import java.util.Optional;

public interface AlimentacionService {
    AlimentacionDTO crearAlimentacion(AlimentacionDTO dto);
    Optional<AlimentacionDTO> obtenerPorEmail(String emailUsuario);
}
