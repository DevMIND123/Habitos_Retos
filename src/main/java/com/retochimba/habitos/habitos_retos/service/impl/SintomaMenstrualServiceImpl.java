package com.retochimba.habitos.habitos_retos.service.impl;

import com.retochimba.habitos.habitos_retos.dto.SintomaMenstrualDTO;
import com.retochimba.habitos.habitos_retos.model.SintomaMenstrual;
import com.retochimba.habitos.habitos_retos.repository.SintomaMenstrualRepository;
import com.retochimba.habitos.habitos_retos.service.SintomaMenstrualService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SintomaMenstrualServiceImpl implements SintomaMenstrualService {

    private final SintomaMenstrualRepository repository;

    @Override
    public SintomaMenstrualDTO registrarSintoma(SintomaMenstrualDTO dto) {
        SintomaMenstrual sintoma = SintomaMenstrual.builder()
                .tipo(dto.getTipo())
                .fecha(dto.getFecha())
                .intensidad(dto.getIntensidad())
                .emailUsuario(dto.getEmailUsuario())
                .build();

        sintoma = repository.save(sintoma);
        dto.setId(sintoma.getId());
        return dto;
    }

    @Override
    public List<SintomaMenstrualDTO> obtenerSintomasPorUsuario(String emailUsuario) {
        return repository.findByEmailUsuarioOrderByFechaDesc(emailUsuario)
                .stream()
                .map(s -> SintomaMenstrualDTO.builder()
                        .id(s.getId())
                        .tipo(s.getTipo())
                        .fecha(s.getFecha())
                        .intensidad(s.getIntensidad())
                        .emailUsuario(s.getEmailUsuario())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<SintomaMenstrualDTO> obtenerSintomasPorRango(String emailUsuario, LocalDate inicio, LocalDate fin) {
        return repository.findByEmailUsuarioAndFechaBetween(emailUsuario, inicio, fin)
                .stream()
                .map(s -> SintomaMenstrualDTO.builder()
                        .id(s.getId())
                        .tipo(s.getTipo())
                        .fecha(s.getFecha())
                        .intensidad(s.getIntensidad())
                        .emailUsuario(s.getEmailUsuario())
                        .build())
                .collect(Collectors.toList());
    }
}
