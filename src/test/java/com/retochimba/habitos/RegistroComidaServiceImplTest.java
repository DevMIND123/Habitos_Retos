package com.retochimba.habitos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.retochimba.habitos.habitos_retos.dto.RegistroComidaDTO;
import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import com.retochimba.habitos.habitos_retos.model.RegistroComida;
import com.retochimba.habitos.habitos_retos.repository.AlimentacionRepository;
import com.retochimba.habitos.habitos_retos.repository.RegistroComidaRepository;
import com.retochimba.habitos.habitos_retos.service.impl.RegistroComidaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RegistroComidaServiceImplTest {

    @Mock
    private RegistroComidaRepository comidaRepository;

    @Mock
    private AlimentacionRepository alimentacionRepository;

    @InjectMocks
    private RegistroComidaServiceImpl registroComidaService;

    private Alimentacion alimentacion;
    private RegistroComida comida;
    private RegistroComidaDTO comidaDTO;

    @BeforeEach
    void setUp() {
        alimentacion = Alimentacion.builder()
                .id(1L)
                .caloriasConsumidasHoy(500)
                .build();

        comida = RegistroComida.builder()
                .id(1L)
                .nombre("Ensalada César")
                .calorias(350)
                .fechaHoraRegistro(LocalDateTime.now())
                .habitoAlimentacion(alimentacion)
                .build();

        comidaDTO = RegistroComidaDTO.builder()
                .nombre("Ensalada César")
                .calorias(350)
                .fechaHoraRegistro(LocalDateTime.now())
                .build();
    }

    @Test
    void registrarComida() {
        // Arrange
        when(alimentacionRepository.findById(1L)).thenReturn(Optional.of(alimentacion));
        when(comidaRepository.save(any(RegistroComida.class))).thenReturn(comida);

        // Act
        RegistroComidaDTO result = registroComidaService.registrarComida(1L, comidaDTO);

        // Assert
        assertEquals(comida.getId(), result.getId());
        assertEquals(850, alimentacion.getCaloriasConsumidasHoy()); // 500 + 350
        verify(comidaRepository, times(1)).save(any(RegistroComida.class));
        verify(alimentacionRepository, times(1)).save(alimentacion);
    }

    @Test
    void registrarComida_NotFound() {
        // Arrange
        when(alimentacionRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> registroComidaService.registrarComida(99L, comidaDTO));
        verify(comidaRepository, never()).save(any());
    }

    @Test
    void registrarComida_CaloriasNegativas() {
        // Arrange
        comidaDTO.setCalorias(-100);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> registroComidaService.registrarComida(1L, comidaDTO));
        verify(comidaRepository, never()).save(any());
    }

    @Test
    void obtenerComidasPorAlimentacion() {
        // Arrange
        when(comidaRepository.findByHabitoAlimentacionId(1L))
                .thenReturn(List.of(comida));

        // Act
        List<RegistroComidaDTO> result = registroComidaService.obtenerComidasPorAlimentacion(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(comida.getNombre(), result.get(0).getNombre());
        verify(comidaRepository, times(1)).findByHabitoAlimentacionId(1L);
    }

    @Test
    void obtenerComidasPorAlimentacion_SinComidas() {
        // Arrange
        when(comidaRepository.findByHabitoAlimentacionId(1L)).thenReturn(List.of());

        // Act
        List<RegistroComidaDTO> result = registroComidaService.obtenerComidasPorAlimentacion(1L);

        // Assert
        assertTrue(result.isEmpty());
    }
}