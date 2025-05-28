package com.retochimba.habitos.habitos_retos.dto;

import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmbarazoRequestDTO {
    private String emailUsuario;
    private LocalDate fechaInicio;
    private String sintomas;
}
