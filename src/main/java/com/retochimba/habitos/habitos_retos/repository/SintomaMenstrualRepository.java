package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.SintomaMenstrual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SintomaMenstrualRepository extends JpaRepository<SintomaMenstrual, Long> {
    List<SintomaMenstrual> findByEmailUsuarioOrderByFechaDesc(String emailUsuario);
    List<SintomaMenstrual> findByEmailUsuarioAndFechaBetween(String emailUsuario, LocalDate inicio, LocalDate fin);
}
