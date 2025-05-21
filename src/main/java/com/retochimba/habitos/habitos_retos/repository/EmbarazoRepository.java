package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.Embarazo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmbarazoRepository extends JpaRepository<Embarazo, Long> {
    Optional<Embarazo> findByEmailUsuario(String emailUsuario);
    Optional<Embarazo> findTopByEmailUsuarioOrderByFechaInicioDesc(String emailUsuario);
}