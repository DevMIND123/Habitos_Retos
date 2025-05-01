package com.retochimba.habitos.habitos_retos.service;

import com.retochimba.habitos.habitos_retos.dto.CicloMenstrualDTO;
import model.CicloMenstrual;
import java.util.List;

public interface CicloMenstrualService {
    CicloMenstrual registrarCiclo(CicloMenstrualDTO dto);
    List<CicloMenstrual> obtenerCiclosPorUsuario(String emailUsuario);
}