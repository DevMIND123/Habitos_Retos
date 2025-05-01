package com.retochimba.habitos.habitos_retos.service.impl;

import com.retochimba.habitos.habitos_retos.dto.CicloMenstrualDTO;
import model.CicloMenstrual;
import com.retochimba.habitos.habitos_retos.repository.CicloMenstrualRepository;
import com.retochimba.habitos.habitos_retos.service.CicloMenstrualService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CicloMenstrualServiceImpl implements CicloMenstrualService {

    private final CicloMenstrualRepository repository;

    @Override
    public CicloMenstrual registrarCiclo(CicloMenstrualDTO dto) {
        LocalDate fechaOvulacion = dto.getFechaInicio().plusDays(dto.getDuracionCiclo() / 2);
        LocalDate fechaProximaMenstruacion = dto.getFechaInicio().plusDays(dto.getDuracionCiclo());

        CicloMenstrual ciclo = CicloMenstrual.builder()
                .emailUsuario(dto.getEmailUsuario())
                .fechaInicio(dto.getFechaInicio())
                .duracionCiclo(dto.getDuracionCiclo())
                .duracionMenstruacion(dto.getDuracionMenstruacion())
                .fechaProximaMenstruacion(fechaProximaMenstruacion)
                .fechaOvulacion(fechaOvulacion)
                .build();

        return repository.save(ciclo);
    }

    @Override
    public List<CicloMenstrual> obtenerCiclosPorUsuario(String emailUsuario) {
    return repository.findByEmailUsuarioOrderByFechaInicioDesc(emailUsuario);
    }
}
