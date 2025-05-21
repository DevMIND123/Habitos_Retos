package com.retochimba.habitos;

import com.retochimba.habitos.habitos_retos.dto.SintomaMenstrualDTO;
import com.retochimba.habitos.habitos_retos.model.SintomaMenstrual;
import com.retochimba.habitos.habitos_retos.model.enums.SintomaTipo;
import com.retochimba.habitos.habitos_retos.repository.SintomaMenstrualRepository;
import com.retochimba.habitos.habitos_retos.service.impl.SintomaMenstrualServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SintomaMenstrualServiceImplTest {

    @Mock
    private SintomaMenstrualRepository repository;

    @InjectMocks
    private SintomaMenstrualServiceImpl service;

    private SintomaMenstrualDTO sintomaDTO;
    private SintomaMenstrual sintoma;

    @BeforeEach
    void setUp() {
        sintomaDTO = SintomaMenstrualDTO.builder()
                .tipo(SintomaTipo.MIGRAÑA)
                .fecha(LocalDate.now())
                .intensidad("alta")
                .emailUsuario("usuario@test.com")
                .build();

        sintoma = SintomaMenstrual.builder()
                .id(1L)
                .tipo(SintomaTipo.MIGRAÑA)
                .fecha(LocalDate.now())
                .intensidad("alta")
                .emailUsuario("usuario@test.com")
                .build();
    }

    @Test
    void registrarSintoma() {
        // Arrange
        when(repository.save(any(SintomaMenstrual.class))).thenReturn(sintoma);

        // Act
        SintomaMenstrualDTO resultado = service.registrarSintoma(sintomaDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(SintomaTipo.MIGRAÑA, resultado.getTipo());
        assertEquals("alta", resultado.getIntensidad());
        assertEquals("usuario@test.com", resultado.getEmailUsuario());

        verify(repository, times(1)).save(any(SintomaMenstrual.class));
    }

    @Test
    void obtenerSintomasPorUsuario() {
        // Arrange
        String emailUsuario = "usuario@test.com";
        List<SintomaMenstrual> sintomas = Arrays.asList(
                sintoma,
                SintomaMenstrual.builder()
                        .id(2L)
                        .tipo(SintomaTipo.FATIGA)
                        .fecha(LocalDate.now().minusDays(1))
                        .intensidad("media")
                        .emailUsuario(emailUsuario)
                        .build()
        );

        when(repository.findByEmailUsuarioOrderByFechaDesc(emailUsuario)).thenReturn(sintomas);

        // Act
        List<SintomaMenstrualDTO> resultados = service.obtenerSintomasPorUsuario(emailUsuario);

        // Assert
        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertEquals(1L, resultados.get(0).getId()); // Verifica orden descendente por fecha
        assertEquals(SintomaTipo.MIGRAÑA, resultados.get(0).getTipo());
        assertEquals(SintomaTipo.FATIGA, resultados.get(1).getTipo());

        verify(repository, times(1)).findByEmailUsuarioOrderByFechaDesc(emailUsuario);
    }

    @Test
    void obtenerSintomasPorUsuario_SinRegistros() {
        // Arrange
        String emailUsuario = "noexiste@test.com";
        when(repository.findByEmailUsuarioOrderByFechaDesc(emailUsuario)).thenReturn(List.of());

        // Act
        List<SintomaMenstrualDTO> resultados = service.obtenerSintomasPorUsuario(emailUsuario);

        // Assert
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
        verify(repository, times(1)).findByEmailUsuarioOrderByFechaDesc(emailUsuario);
    }

    @Test
    void obtenerSintomasPorRango() {
        // Arrange
        String emailUsuario = "usuario@test.com";
        LocalDate inicio = LocalDate.now().minusDays(7);
        LocalDate fin = LocalDate.now();
        
        List<SintomaMenstrual> sintomas = Arrays.asList(
                sintoma,
                SintomaMenstrual.builder()
                        .id(2L)
                        .tipo(SintomaTipo.DOLOR_PELVICO)
                        .fecha(LocalDate.now().minusDays(3))
                        .intensidad("baja")
                        .emailUsuario(emailUsuario)
                        .build()
        );

        when(repository.findByEmailUsuarioAndFechaBetween(emailUsuario, inicio, fin)).thenReturn(sintomas);

        // Act
        List<SintomaMenstrualDTO> resultados = service.obtenerSintomasPorRango(emailUsuario, inicio, fin);

        // Assert
        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertTrue(resultados.stream().allMatch(s -> 
            !s.getFecha().isBefore(inicio) && !s.getFecha().isAfter(fin)));
        assertEquals(emailUsuario, resultados.get(0).getEmailUsuario());
        assertEquals(emailUsuario, resultados.get(1).getEmailUsuario());

        verify(repository, times(1)).findByEmailUsuarioAndFechaBetween(emailUsuario, inicio, fin);
    }

    @Test
    void obtenerSintomasPorRango_SinRegistros() {
        // Arrange
        String emailUsuario = "usuario@test.com";
        LocalDate inicio = LocalDate.now().minusDays(30);
        LocalDate fin = LocalDate.now().minusDays(15);
        when(repository.findByEmailUsuarioAndFechaBetween(emailUsuario, inicio, fin)).thenReturn(List.of());

        // Act
        List<SintomaMenstrualDTO> resultados = service.obtenerSintomasPorRango(emailUsuario, inicio, fin);

        // Assert
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
        verify(repository, times(1)).findByEmailUsuarioAndFechaBetween(emailUsuario, inicio, fin);
    }
}