package com.retochimba.habitos.habitos_retos.dto;

import com.retochimba.habitos.habitos_retos.model.enums.SintomaTipo;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SintomaMenstrualDTO {
    private Long id;
    private SintomaTipo tipo;
    private LocalDate fecha;
    private String intensidad;
    private String emailUsuario;
}
