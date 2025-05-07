package com.retochimba.habitos.habitos_retos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "retos_alimentacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetoAlimentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion; // Ej: "Comer 3 frutas al día"

    private boolean completado;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "habito_alimentacion_id")
    private Alimentacion habitoAlimentacion;
}
