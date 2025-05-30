package com.retochimba.habitos.habitos_retos.service;

import com.retochimba.habitos.habitos_retos.dto.AlimentacionDTO;

import java.util.List;

public interface AlimentacionService {
    AlimentacionDTO crearAlimentacion(AlimentacionDTO dto);
    List<AlimentacionDTO> obtenerTodosPorEmail(String emailUsuario);
}
