package com.retochimba.habitos.habitos_retos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "habitos_alimentacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alimentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailUsuario;

    private String objetivo; // "Perder peso", "Ganar masa", "Mantener peso"

    @Column(nullable = true)
    private float peso;
    @Column(nullable = true)
    private float altura;
    @Column(nullable = true)
    private float imc;
    
    private Integer caloriasObjetivoDiarias;

    private Integer caloriasConsumidasHoy;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @OneToMany(mappedBy = "habitoAlimentacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RetoAlimentacion> retosAsignados;

    @OneToMany(mappedBy = "habitoAlimentacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroComida> comidasRegistradas;
}
