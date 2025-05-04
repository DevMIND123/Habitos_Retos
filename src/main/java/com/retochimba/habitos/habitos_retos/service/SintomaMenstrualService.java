package com.retochimba.habitos.habitos_retos.service;

import com.retochimba.habitos.habitos_retos.dto.SintomaMenstrualDTO;

import java.time.LocalDate;
import java.util.List;

public interface SintomaMenstrualService {
    SintomaMenstrualDTO registrarSintoma(SintomaMenstrualDTO dto);
    List<SintomaMenstrualDTO> obtenerSintomasPorUsuario(String emailUsuario);
    List<SintomaMenstrualDTO> obtenerSintomasPorRango(String emailUsuario, LocalDate inicio, LocalDate fin);
}
