package com.retochimba.habitos.habitos_retos.dto;


import com.retochimba.habitos.habitos_retos.model.enums.EventoTipo;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoMenstrualDTO {
    private Long id;
    private EventoTipo tipo;
    private LocalDate fecha;
    private String observaciones;
    private String emailUsuario;
}
