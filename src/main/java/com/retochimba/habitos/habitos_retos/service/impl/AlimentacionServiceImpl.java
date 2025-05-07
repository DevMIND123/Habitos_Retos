package com.retochimba.habitos.habitos_retos.service.impl;

import com.retochimba.habitos.habitos_retos.dto.AlimentacionDTO;
import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import com.retochimba.habitos.habitos_retos.repository.AlimentacionRepository;
import com.retochimba.habitos.habitos_retos.service.AlimentacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlimentacionServiceImpl implements AlimentacionService {

    private final AlimentacionRepository alimentacionRepository;

    @Override
    public AlimentacionDTO crearAlimentacion(AlimentacionDTO dto) {
        Alimentacion alimentacion = Alimentacion.builder()
                .emailUsuario(dto.getEmailUsuario())
                .objetivo(dto.getObjetivo())
                .caloriasObjetivoDiarias(dto.getCaloriasObjetivoDiarias())
                .caloriasConsumidasHoy(0)
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .build();

        alimentacion = alimentacionRepository.save(alimentacion);

        dto.setId(alimentacion.getId());
        dto.setCaloriasConsumidasHoy(0); // inicializado
        return dto;
    }

    @Override
    public List<AlimentacionDTO> obtenerTodosPorEmail(String emailUsuario) {
        return alimentacionRepository.findByEmailUsuario(emailUsuario).stream()
                .map(al -> AlimentacionDTO.builder()
                        .id(al.getId())
                        .emailUsuario(al.getEmailUsuario())
                        .objetivo(al.getObjetivo())
                        .caloriasObjetivoDiarias(al.getCaloriasObjetivoDiarias())
                        .caloriasConsumidasHoy(al.getCaloriasConsumidasHoy())
                        .fechaInicio(al.getFechaInicio())
                        .fechaFin(al.getFechaFin())
                        .build())
                .toList();
    }

}
