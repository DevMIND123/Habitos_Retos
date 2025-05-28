package com.retochimba.habitos.habitos_retos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "embarazos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Embarazo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailUsuario;

    private LocalDate fechaInicio;

    private LocalDate fechaPartoEstimada;

    private Integer semanaActual;

    private String sintomas;

    @Column(name = "ultima_semana_notificada")
    private Integer ultimaSemanaNotificada;
}