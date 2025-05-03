package com.retochimba.habitos.habitos_retos.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetoAlimentacionDTO {
    private Long id;
    private String descripcion;
    private boolean completado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
