package com.retochimba.habitos.habitos_retos.service.impl;

import com.retochimba.habitos.habitos_retos.dto.RegistroComidaDTO;
import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import com.retochimba.habitos.habitos_retos.model.RegistroComida;
import com.retochimba.habitos.habitos_retos.repository.AlimentacionRepository;
import com.retochimba.habitos.habitos_retos.repository.RegistroComidaRepository;
import com.retochimba.habitos.habitos_retos.service.RegistroComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroComidaServiceImpl implements RegistroComidaService {

    private final RegistroComidaRepository comidaRepository;
    private final AlimentacionRepository alimentacionRepository;

    @Override
    public RegistroComidaDTO registrarComida(Long alimentacionId, RegistroComidaDTO dto) {
        Alimentacion alimentacion = alimentacionRepository.findById(alimentacionId)
                .orElseThrow(() -> new RuntimeException("Alimentación no encontrada"));

        RegistroComida comida = RegistroComida.builder()
                .nombre(dto.getNombre())
                .calorias(dto.getCalorias())
                .fechaHoraRegistro(dto.getFechaHoraRegistro())
                .habitoAlimentacion(alimentacion)
                .build();

        comidaRepository.save(comida);

        // Actualizar calorías consumidas
        alimentacion.setCaloriasConsumidasHoy(
                alimentacion.getCaloriasConsumidasHoy() + dto.getCalorias()
        );
        alimentacionRepository.save(alimentacion);

        dto.setId(comida.getId());
        return dto;
    }

    @Override
    public List<RegistroComidaDTO> obtenerComidasPorAlimentacion(Long alimentacionId) {
        return comidaRepository.findByHabitoAlimentacionId(alimentacionId)
                .stream()
                .map(comida -> RegistroComidaDTO.builder()
                        .id(comida.getId())
                        .nombre(comida.getNombre())
                        .calorias(comida.getCalorias())
                        .fechaHoraRegistro(comida.getFechaHoraRegistro())
                        .build())
                .collect(Collectors.toList());
    }
}
