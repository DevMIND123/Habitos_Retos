package com.retochimba.habitos.habitos_retos.kafka.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CicloRegistradoEvent {
    private String emailUsuario;
    private String mensaje; // ejemplo: "Tu nuevo ciclo ha sido registrado con éxito"
}