package com.retochimba.habitos.habitos_retos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ciclos_menstruales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CicloMenstrual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailUsuario;    // Referencia al usuario externo

    private LocalDate fechaInicio;        // Día 1 del ciclo
    private int duracionCiclo;            // Ej: 28 días
    private int duracionMenstruacion;     // Ej: 5 días

    // Fecha estimada para mostrar en el frontend
    private LocalDate fechaProximaMenstruacion;
    private LocalDate fechaOvulacion;
}
