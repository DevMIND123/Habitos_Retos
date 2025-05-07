package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.EventoMenstrual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventoMenstrualRepository extends JpaRepository<EventoMenstrual, Long> {
    List<EventoMenstrual> findByEmailUsuarioOrderByFechaDesc(String emailUsuario);
    List<EventoMenstrual> findByEmailUsuarioAndFechaBetween(String emailUsuario, LocalDate inicio, LocalDate fin);
}
