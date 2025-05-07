package com.retochimba.habitos.habitos_retos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_comidas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Ej: "Desayuno", "Ensalada", etc.

    private Integer calorias;

    private LocalDateTime fechaHoraRegistro;

    @ManyToOne
    @JoinColumn(name = "habito_alimentacion_id")
    private Alimentacion habitoAlimentacion;
}
