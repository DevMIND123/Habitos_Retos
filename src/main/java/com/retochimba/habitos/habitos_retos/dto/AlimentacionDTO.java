package com.retochimba.habitos.habitos_retos.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentacionDTO {
    private Long id;
    private String emailUsuario;
    private String objetivo;
    private float peso; 
    private float altura;
    private float Imc;
    private Integer caloriasObjetivoDiarias;
    private Integer caloriasConsumidasHoy;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<RetoAlimentacionDTO> retosAsignados;
    private List<RegistroComidaDTO> comidasRegistradas;
}
