package com.retochimba.habitos.habitos_retos.service;

import com.retochimba.habitos.habitos_retos.dto.EventoMenstrualDTO;

import java.time.LocalDate;
import java.util.List;

public interface EventoMenstrualService {
    EventoMenstrualDTO registrarEvento(EventoMenstrualDTO dto);
    List<EventoMenstrualDTO> obtenerEventosPorUsuario(String emailUsuario);
    List<EventoMenstrualDTO> obtenerEventosPorRango(String emailUsuario, LocalDate inicio, LocalDate fin);
}
