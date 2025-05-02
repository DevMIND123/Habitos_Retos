package com.retochimba.habitos.habitos_retos.repository;

import com.retochimba.habitos.habitos_retos.model.CicloMenstrual;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CicloMenstrualRepository extends JpaRepository<CicloMenstrual, Long> {
    List<CicloMenstrual> findByEmailUsuarioOrderByFechaInicioDesc(String emailUsuario);
}