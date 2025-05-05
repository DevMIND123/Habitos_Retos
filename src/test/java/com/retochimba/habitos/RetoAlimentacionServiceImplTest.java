package com.retochimba.habitos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.retochimba.habitos.habitos_retos.dto.RetoAlimentacionDTO;
import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import com.retochimba.habitos.habitos_retos.model.RetoAlimentacion;
import com.retochimba.habitos.habitos_retos.repository.AlimentacionRepository;
import com.retochimba.habitos.habitos_retos.repository.RetoAlimentacionRepository;
import com.retochimba.habitos.habitos_retos.service.impl.RetoAlimentacionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RetoAlimentacionServiceImplTest {

    @Mock
    private RetoAlimentacionRepository retoRepository;

    @Mock
    private AlimentacionRepository alimentacionRepository;

    @InjectMocks
    private RetoAlimentacionServiceImpl retoService;

    private Alimentacion alimentacion;
    private RetoAlimentacion reto;
    private RetoAlimentacionDTO retoDTO;

    @BeforeEach
    void setUp() {
        alimentacion = Alimentacion.builder()
                .id(1L)
                .build();

        reto = RetoAlimentacion.builder()
                .id(1L)
                .descripcion("Consumir 5 porciones de vegetales al día")
                .completado(false)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(7))
                .habitoAlimentacion(alimentacion)
                .build();

        retoDTO = RetoAlimentacionDTO.builder()
                .descripcion("Consumir 5 porciones de vegetales al día")
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(7))
                .build();
    }

    @Test
    void asignarReto() {
        // Arrange
        when(alimentacionRepository.findById(1L)).thenReturn(Optional.of(alimentacion));
        when(retoRepository.save(any(RetoAlimentacion.class))).thenReturn(reto);

        // Act
        RetoAlimentacionDTO result = retoService.asignarReto(1L, retoDTO);

        // Assert
        assertEquals(reto.getId(), result.getId());
        assertFalse(result.isCompletado()); // Por defecto no completado
        verify(retoRepository, times(1)).save(any(RetoAlimentacion.class));
    }

    @Test
    void asignarReto_IdInvalido() {
        // Arrange
        when(alimentacionRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> retoService.asignarReto(99L, retoDTO));
        verify(retoRepository, never()).save(any());
    }

    @Test
    void asignarReto_DateInvalida() {
        // Arrange
        retoDTO.setFechaFin(LocalDate.now().minusDays(1));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> retoService.asignarReto(1L, retoDTO));
        verify(retoRepository, never()).save(any());
    }

    @Test
    void obtenerRetosPorAlimentacion() {
        // Arrange
        when(retoRepository.findByHabitoAlimentacionId(1L))
                .thenReturn(List.of(reto));

        // Act
        List<RetoAlimentacionDTO> result = retoService.obtenerRetosPorAlimentacion(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(reto.getDescripcion(), result.get(0).getDescripcion());
        verify(retoRepository, times(1)).findByHabitoAlimentacionId(1L);
    }

    @Test
    void obtenerRetosPorAlimentacion_SinRegistros() {
        // Arrange
        when(retoRepository.findByHabitoAlimentacionId(1L)).thenReturn(List.of());

        // Act
        List<RetoAlimentacionDTO> result = retoService.obtenerRetosPorAlimentacion(1L);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void completarReto() {
        // Arrange
        when(retoRepository.findById(1L)).thenReturn(Optional.of(reto));
        when(retoRepository.save(reto)).thenReturn(reto);

        // Act
        RetoAlimentacionDTO result = retoService.completarReto(1L);

        // Assert
        assertTrue(result.isCompletado());
        verify(retoRepository, times(1)).save(reto);
    }

    @Test
    void completarReto_IdInvalido() {
        // Arrange
        when(retoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> retoService.completarReto(99L));
        verify(retoRepository, never()).save(any());
    }
}