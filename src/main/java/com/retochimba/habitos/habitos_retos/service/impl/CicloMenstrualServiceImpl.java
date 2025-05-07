package com.retochimba.habitos.habitos_retos.service.impl;

import com.retochimba.habitos.habitos_retos.dto.CicloMenstrualDTO;
import com.retochimba.habitos.habitos_retos.kafka.KafkaProducerService;
import com.retochimba.habitos.habitos_retos.kafka.event.CicloRegistradoEvent;

import com.retochimba.habitos.habitos_retos.model.CicloMenstrual;
import com.retochimba.habitos.habitos_retos.repository.CicloMenstrualRepository;
import com.retochimba.habitos.habitos_retos.service.CicloMenstrualService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CicloMenstrualServiceImpl implements CicloMenstrualService {

    private final CicloMenstrualRepository repository;
    private final KafkaProducerService kafkaProducer;

        @Override
        public CicloMenstrual registrarCiclo(CicloMenstrualDTO dto) {
        LocalDate fechaOvulacion = dto.getFechaInicio().plusDays(dto.getDuracionCiclo() / 2);
        LocalDate fechaProximaMenstruacion = dto.getFechaInicio().plusDays(dto.getDuracionCiclo());

        CicloMenstrual ciclo = CicloMenstrual.builder()
                .emailUsuario(dto.getEmailUsuario())
                .fechaInicio(dto.getFechaInicio())
                .duracionCiclo(dto.getDuracionCiclo())
                .duracionMenstruacion(dto.getDuracionMenstruacion())
                .fechaProximaMenstruacion(fechaProximaMenstruacion)
                .fechaOvulacion(fechaOvulacion)
                .build();

        CicloMenstrual guardado = repository.save(ciclo);

        // 🔁 Enviar evento Kafka
        CicloRegistradoEvent evento = CicloRegistradoEvent.builder()
                .emailUsuario(dto.getEmailUsuario())
                .mensaje("🩸 Tu nuevo ciclo menstrual ha sido registrado correctamente.")
                .build();

        kafkaProducer.enviarEvento(evento);

        return guardado;
    }


    @Override
    public List<CicloMenstrual> obtenerCiclosPorUsuario(String emailUsuario) {
    return repository.findByEmailUsuarioOrderByFechaInicioDesc(emailUsuario);
    }
}
