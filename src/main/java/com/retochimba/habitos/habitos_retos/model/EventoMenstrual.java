package com.retochimba.habitos.habitos_retos.model;

import com.retochimba.habitos.habitos_retos.model.enums.EventoTipo;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "eventos_menstruales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoMenstrual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventoTipo tipo;

    private LocalDate fecha;

    private String observaciones;

    private String emailUsuario;  // Para relacionarlo directamente con el usuario
}
