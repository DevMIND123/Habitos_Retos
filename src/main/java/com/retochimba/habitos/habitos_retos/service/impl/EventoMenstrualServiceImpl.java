package com.retochimba.habitos.habitos_retos.service.impl;

import com.retochimba.habitos.habitos_retos.dto.EventoMenstrualDTO;
import com.retochimba.habitos.habitos_retos.model.EventoMenstrual;
import com.retochimba.habitos.habitos_retos.repository.EventoMenstrualRepository;
import com.retochimba.habitos.habitos_retos.service.EventoMenstrualService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoMenstrualServiceImpl implements EventoMenstrualService {

        private final EventoMenstrualRepository repository;

        @Override
        public EventoMenstrualDTO registrarEvento(EventoMenstrualDTO dto) {
                if (dto.getEmailUsuario() == null || dto.getEmailUsuario().isBlank()) {
                        throw new IllegalArgumentException("El email del usuario es obligatorio.");
                }
                EventoMenstrual evento = EventoMenstrual.builder()
                                .tipo(dto.getTipo())
                                .fecha(dto.getFecha())
                                .observaciones(dto.getObservaciones())
                                .emailUsuario(dto.getEmailUsuario())
                                .build();

                evento = repository.save(evento);
                dto.setId(evento.getId());
                return dto;
        }

        @Override
        public List<EventoMenstrualDTO> obtenerEventosPorUsuario(String emailUsuario) {
                return repository.findByEmailUsuarioOrderByFechaDesc(emailUsuario)
                                .stream()
                                .map(evento -> EventoMenstrualDTO.builder()
                                                .id(evento.getId())
                                                .tipo(evento.getTipo())
                                                .fecha(evento.getFecha())
                                                .observaciones(evento.getObservaciones())
                                                .emailUsuario(evento.getEmailUsuario())
                                                .build())
                                .collect(Collectors.toList());
        }

        @Override
        public List<EventoMenstrualDTO> obtenerEventosPorRango(String emailUsuario, LocalDate inicio, LocalDate fin) {
                return repository.findByEmailUsuarioAndFechaBetween(emailUsuario, inicio, fin)
                                .stream()
                                .map(evento -> EventoMenstrualDTO.builder()
                                                .id(evento.getId())
                                                .tipo(evento.getTipo())
                                                .fecha(evento.getFecha())
                                                .observaciones(evento.getObservaciones())
                                                .emailUsuario(evento.getEmailUsuario())
                                                .build())
                                .collect(Collectors.toList());
        }
}
