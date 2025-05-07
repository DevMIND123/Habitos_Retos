package com.retochimba.habitos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.retochimba.habitos.habitos_retos.dto.CicloMenstrualDTO;
import com.retochimba.habitos.habitos_retos.kafka.KafkaProducerService;
import com.retochimba.habitos.habitos_retos.kafka.event.CicloRegistradoEvent;
import com.retochimba.habitos.habitos_retos.model.CicloMenstrual;
import com.retochimba.habitos.habitos_retos.repository.CicloMenstrualRepository;
import com.retochimba.habitos.habitos_retos.service.impl.CicloMenstrualServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

public class CicloMenstrualServiceImplTest {

    private CicloMenstrualRepository repository;
    private KafkaProducerService kafkaProducer;
    private CicloMenstrualServiceImpl service;

    @BeforeEach
    public void setUp() {
        repository = mock(CicloMenstrualRepository.class);
        kafkaProducer = mock(KafkaProducerService.class);
        service = new CicloMenstrualServiceImpl(repository, kafkaProducer);
    }

    @Test
    public void testRegistrarCiclo() {
        CicloMenstrualDTO dto = new CicloMenstrualDTO();
        dto.setEmailUsuario("test@example.com");
        dto.setFechaInicio(LocalDate.of(2024, 5, 1));
        dto.setDuracionCiclo(28);
        dto.setDuracionMenstruacion(5);

        CicloMenstrual esperado = CicloMenstrual.builder()
                .emailUsuario("test@example.com")
                .fechaInicio(dto.getFechaInicio())
                .duracionCiclo(28)
                .duracionMenstruacion(5)
                .fechaOvulacion(dto.getFechaInicio().plusDays(14))
                .fechaProximaMenstruacion(dto.getFechaInicio().plusDays(28))
                .build();

        when(repository.save(any())).thenReturn(esperado);

        CicloMenstrual resultado = service.registrarCiclo(dto);

        assertNotNull(resultado);
        assertEquals("test@example.com", resultado.getEmailUsuario());
        assertEquals(LocalDate.of(2024, 5, 15), resultado.getFechaOvulacion());
        assertEquals(LocalDate.of(2024, 5, 29), resultado.getFechaProximaMenstruacion());

        verify(kafkaProducer, times(1)).enviarEvento(any(CicloRegistradoEvent.class));
        verify(repository, times(1)).save(any(CicloMenstrual.class));
    }

    @Test
    public void testObtenerCiclosPorUsuario() {
        CicloMenstrual ciclo1 = CicloMenstrual.builder()
                .emailUsuario("user1@example.com")
                .fechaInicio(LocalDate.of(2024, 4, 1))
                .duracionCiclo(28)
                .duracionMenstruacion(5)
                .build();

        CicloMenstrual ciclo2 = CicloMenstrual.builder()
                .emailUsuario("user1@example.com")
                .fechaInicio(LocalDate.of(2024, 3, 1))
                .duracionCiclo(28)
                .duracionMenstruacion(5)
                .build();

        when(repository.findByEmailUsuarioOrderByFechaInicioDesc("user1@example.com"))
                .thenReturn(Arrays.asList(ciclo1, ciclo2));

        List<CicloMenstrual> ciclos = service.obtenerCiclosPorUsuario("user1@example.com");

        assertEquals(2, ciclos.size());
        assertEquals(LocalDate.of(2024, 4, 1), ciclos.get(0).getFechaInicio());
        verify(repository, times(1)).findByEmailUsuarioOrderByFechaInicioDesc("user1@example.com");
    }
}
