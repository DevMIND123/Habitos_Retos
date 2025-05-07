package com.retochimba.habitos.habitos_retos.service.impl;

import com.retochimba.habitos.habitos_retos.dto.AlimentacionDTO;
import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import com.retochimba.habitos.habitos_retos.repository.AlimentacionRepository;
import com.retochimba.habitos.habitos_retos.service.AlimentacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlimentacionServiceImpl implements AlimentacionService {

    private final AlimentacionRepository alimentacionRepository;

    @Override
    public AlimentacionDTO crearAlimentacion(AlimentacionDTO dto) {
        dto.setImc((float) (dto.getPeso() / Math.pow(dto.getAltura(), 2)));
        float imc = dto.getImc();
        String objetivo;
        float caloriasDiarias;
    
        if (imc < 18.5) {
            objetivo = "Ganar masa";
            caloriasDiarias = dto.getPeso() * 37f; // promedio entre 35-40
        } else if (imc < 25) {
            objetivo = "Mantener peso";
            caloriasDiarias = dto.getPeso() * 30f;
        } else {
            objetivo = "Perder peso";
            caloriasDiarias = dto.getPeso() * 22f; // promedio entre 20-25
        }
    
        dto.setObjetivo(objetivo);
        dto.setCaloriasObjetivoDiarias(Math.round(caloriasDiarias)); // redondeamos a entero si es float
    
        Alimentacion alimentacion = Alimentacion.builder()
                .emailUsuario(dto.getEmailUsuario())
                .objetivo(dto.getObjetivo())
                .peso(dto.getPeso())
                .altura(dto.getAltura())
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
                        .peso(al.getPeso())
                        .altura(al.getAltura())
                        .caloriasObjetivoDiarias(al.getCaloriasObjetivoDiarias())
                        .caloriasConsumidasHoy(al.getCaloriasConsumidasHoy())
                        .fechaInicio(al.getFechaInicio())
                        .fechaFin(al.getFechaFin())
                        .build())
                .toList();
    }

}
