package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlimentacionRepository extends JpaRepository<Alimentacion, Long> {
    Optional<Alimentacion> findByEmailUsuario(String emailUsuario);
}
