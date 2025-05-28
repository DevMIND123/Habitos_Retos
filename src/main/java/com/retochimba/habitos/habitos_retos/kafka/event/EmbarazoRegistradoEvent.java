package com.retochimba.habitos.habitos_retos.kafka.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmbarazoRegistradoEvent {
    private String emailUsuario;
    private String mensaje;
}