package com.retochimba.habitos;

import com.retochimba.habitos.habitos_retos.dto.AlimentacionDTO;
import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import com.retochimba.habitos.habitos_retos.repository.AlimentacionRepository;
import com.retochimba.habitos.habitos_retos.service.impl.AlimentacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlimentacionServiceImplTest {

    private AlimentacionRepository repository;
    private AlimentacionServiceImpl service;

    @BeforeEach
    public void setUp() {
        repository = mock(AlimentacionRepository.class);
        service = new AlimentacionServiceImpl(repository);
    }

    @Test
    public void testCrearAlimentacion() {
        AlimentacionDTO dto = AlimentacionDTO.builder()
                .emailUsuario("test@correo.com")
                .objetivo("Perder peso")
                .caloriasObjetivoDiarias(2000)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(30))
                .build();

        Alimentacion entidadGuardada = Alimentacion.builder()
                .id(1L)
                .emailUsuario(dto.getEmailUsuario())
                .objetivo(dto.getObjetivo())
                .caloriasObjetivoDiarias(dto.getCaloriasObjetivoDiarias())
                .caloriasConsumidasHoy(0)
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .build();

        when(repository.save(any(Alimentacion.class))).thenReturn(entidadGuardada);

        AlimentacionDTO resultado = service.crearAlimentacion(dto);

        assertNotNull(resultado.getId());
        assertEquals(0, resultado.getCaloriasConsumidasHoy());
        assertEquals("Perder peso", resultado.getObjetivo());

        ArgumentCaptor<Alimentacion> captor = ArgumentCaptor.forClass(Alimentacion.class);
        verify(repository).save(captor.capture());
        assertEquals("test@correo.com", captor.getValue().getEmailUsuario());
    }

    @Test
    public void testObtenerTodosPorEmail() {
        Alimentacion alimentacion = Alimentacion.builder()
                .id(2L)
                .emailUsuario("prueba@correo.com")
                .objetivo("Mantener")
                .caloriasObjetivoDiarias(2200)
                .caloriasConsumidasHoy(500)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusWeeks(1))
                .build();

        when(repository.findByEmailUsuario("prueba@correo.com")).thenReturn(List.of(alimentacion));

        List<AlimentacionDTO> lista = service.obtenerTodosPorEmail("prueba@correo.com");

        assertEquals(1, lista.size());
        assertEquals("Mantener", lista.get(0).getObjetivo());
        assertEquals(2200, lista.get(0).getCaloriasObjetivoDiarias());
        assertEquals(500, lista.get(0).getCaloriasConsumidasHoy());
    }
}
