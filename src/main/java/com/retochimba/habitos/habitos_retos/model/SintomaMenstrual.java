package com.retochimba.habitos.habitos_retos.model;

import com.retochimba.habitos.habitos_retos.model.enums.SintomaTipo;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "sintomas_menstruales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SintomaMenstrual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SintomaTipo tipo;

    private LocalDate fecha;

    private String intensidad; // opcional: baja, media, alta

    private String emailUsuario;  // Para relacionarlo directamente con el usuario
}
