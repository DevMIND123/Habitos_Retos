package com.retochimba.habitos.habitos_retos.service;

import com.retochimba.habitos.habitos_retos.dto.RegistroComidaDTO;

import java.util.List;

public interface RegistroComidaService {
    RegistroComidaDTO registrarComida(Long alimentacionId, RegistroComidaDTO dto);
    List<RegistroComidaDTO> obtenerComidasPorAlimentacion(Long alimentacionId);
}
