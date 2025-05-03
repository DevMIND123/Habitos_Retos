package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.RegistroComida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroComidaRepository extends JpaRepository<RegistroComida, Long> {
    List<RegistroComida> findByHabitoAlimentacionId(Long alimentacionId);

}
