package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.Alimentacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlimentacionRepository extends JpaRepository<Alimentacion, Long> {
    List<Alimentacion> findByEmailUsuario(String emailUsuario);
}
