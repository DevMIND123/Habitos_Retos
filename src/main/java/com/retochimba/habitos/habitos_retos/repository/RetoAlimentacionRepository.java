package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.RetoAlimentacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetoAlimentacionRepository extends JpaRepository<RetoAlimentacion, Long> {
    List<RetoAlimentacion> findByHabitoAlimentacionId(Long alimentacionId);
}
