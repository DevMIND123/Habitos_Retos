package com.retochimba.habitos.habitos_retos.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroComidaDTO {
    private Long id;
    private String nombre;
    private Integer calorias;
    private LocalDateTime fechaHoraRegistro;
}
