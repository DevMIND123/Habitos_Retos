package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlimentacionRepository extends JpaRepository<Alimentacion, Long> {
    List<Alimentacion> findByEmailUsuario(String emailUsuario);
}
