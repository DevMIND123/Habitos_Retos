package com.retochimba.habitos.habitos_retos.service.impl;

import com.retochimba.habitos.habitos_retos.dto.RetoAlimentacionDTO;
import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import com.retochimba.habitos.habitos_retos.model.RetoAlimentacion;
import com.retochimba.habitos.habitos_retos.repository.AlimentacionRepository;
import com.retochimba.habitos.habitos_retos.repository.RetoAlimentacionRepository;
import com.retochimba.habitos.habitos_retos.service.RetoAlimentacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RetoAlimentacionServiceImpl implements RetoAlimentacionService {

    private final RetoAlimentacionRepository retoRepository;
    private final AlimentacionRepository alimentacionRepository;

    @Override
    public RetoAlimentacionDTO asignarReto(Long alimentacionId, RetoAlimentacionDTO dto) {
        Alimentacion alimentacion = alimentacionRepository.findById(alimentacionId)
                .orElseThrow(() -> new RuntimeException("Alimentación no encontrada"));

        RetoAlimentacion reto = RetoAlimentacion.builder()
                .descripcion(dto.getDescripcion())
                .completado(false)
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .habitoAlimentacion(alimentacion)
                .build();

        reto = retoRepository.save(reto);
        dto.setId(reto.getId());
        dto.setCompletado(false);
        return dto;
    }

    @Override
    public List<RetoAlimentacionDTO> obtenerRetosPorAlimentacion(Long alimentacionId) {
        return retoRepository.findByHabitoAlimentacionId(alimentacionId)
                .stream()
                .map(r -> RetoAlimentacionDTO.builder()
                        .id(r.getId())
                        .descripcion(r.getDescripcion())
                        .completado(r.isCompletado())
                        .fechaInicio(r.getFechaInicio())
                        .fechaFin(r.getFechaFin())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RetoAlimentacionDTO completarReto(Long retoId) {
        RetoAlimentacion reto = retoRepository.findById(retoId)
                .orElseThrow(() -> new RuntimeException("Reto no encontrado"));

        reto.setCompletado(true);
        reto = retoRepository.save(reto);

        return RetoAlimentacionDTO.builder()
                .id(reto.getId())
                .descripcion(reto.getDescripcion())
                .completado(true)
                .fechaInicio(reto.getFechaInicio())
                .fechaFin(reto.getFechaFin())
                .build();
    }
}
