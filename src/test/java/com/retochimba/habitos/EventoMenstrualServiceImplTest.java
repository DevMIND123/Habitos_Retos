package com.retochimba.habitos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.retochimba.habitos.habitos_retos.dto.EventoMenstrualDTO;
import com.retochimba.habitos.habitos_retos.model.EventoMenstrual;
import com.retochimba.habitos.habitos_retos.model.enums.EventoTipo;
import com.retochimba.habitos.habitos_retos.repository.EventoMenstrualRepository;
import com.retochimba.habitos.habitos_retos.service.impl.EventoMenstrualServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EventoMenstrualServiceImplTest {

    @Mock
    private EventoMenstrualRepository repository;

    @InjectMocks
    private EventoMenstrualServiceImpl service;

    private EventoMenstrual evento;
    private EventoMenstrualDTO dto;

    @BeforeEach
    void setUp() {
        evento = EventoMenstrual.builder()
                .id(1L)
                .tipo(EventoTipo.INICIO_REGLA)
                .fecha(LocalDate.of(2024, 6, 1))
                .observaciones("Flujo moderado")
                .emailUsuario("usuario@test.com")
                .build();

        dto = EventoMenstrualDTO.builder()
                .tipo(EventoTipo.INICIO_REGLA)
                .fecha(LocalDate.of(2024, 6, 1))
                .observaciones("Flujo moderado")
                .emailUsuario("usuario@test.com")
                .build();
    }

    @Test
    void registrarEvento() {
        // Arrange
        when(repository.save(any(EventoMenstrual.class))).thenReturn(evento);

        // Act
        EventoMenstrualDTO result = service.registrarEvento(dto);

        // Assert
        assertEquals(evento.getId(), result.getId());
        assertEquals(dto.getTipo(), result.getTipo());
        verify(repository, times(1)).save(any(EventoMenstrual.class));
    }

    @Test
    void registrarEvento_EmailVacio() {
        // Arrange
        dto.setEmailUsuario("");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> service.registrarEvento(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void obtenerEventosPorUsuario() {
        // Arrange
        EventoMenstrual evento2 = EventoMenstrual.builder()
                .id(2L)
                .fecha(LocalDate.of(2024, 5, 15))
                .build();

        when(repository.findByEmailUsuarioOrderByFechaDesc("usuario@test.com"))
                .thenReturn(Arrays.asList(evento, evento2));

        // Act
        List<EventoMenstrualDTO> result = service.obtenerEventosPorUsuario("usuario@test.com");

        // Assert
        assertEquals(2, result.size());
        assertEquals(LocalDate.of(2024, 6, 1), result.get(0).getFecha()); // Verifica orden descendente
        verify(repository, times(1)).findByEmailUsuarioOrderByFechaDesc("usuario@test.com");
    }

    @Test
    void obtenerEventosPorRango_WithValidRange_ReturnsFilteredList() {
        // Arrange
        LocalDate inicio = LocalDate.of(2024, 6, 1);
        LocalDate fin = LocalDate.of(2024, 6, 30);
        when(repository.findByEmailUsuarioAndFechaBetween("usuario@test.com", inicio, fin))
                .thenReturn(Arrays.asList(evento));

        // Act
        List<EventoMenstrualDTO> result = service.obtenerEventosPorRango(
                "usuario@test.com", inicio, fin);

        // Assert
        assertEquals(1, result.size());
        assertEquals(inicio, result.get(0).getFecha());
        verify(repository, times(1))
                .findByEmailUsuarioAndFechaBetween("usuario@test.com", inicio, fin);
    }
}