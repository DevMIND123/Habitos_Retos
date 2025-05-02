package com.retochimba.habitos.habitos_retos.service;

import com.retochimba.habitos.habitos_retos.dto.EmbarazoRequestDTO;
import com.retochimba.habitos.habitos_retos.kafka.KafkaProducerService;
import com.retochimba.habitos.habitos_retos.kafka.event.EmbarazoRegistradoEvent;
import com.retochimba.habitos.habitos_retos.model.Embarazo;
import com.retochimba.habitos.habitos_retos.repository.EmbarazoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class EmbarazoService {

    private final EmbarazoRepository repository;
    private final KafkaProducerService kafka;

    public void registrarEmbarazo(EmbarazoRequestDTO request) {
        LocalDate fechaParto = request.getFechaInicio().plusWeeks(40);
        int semanas = (int) ChronoUnit.WEEKS.between(request.getFechaInicio(), LocalDate.now());

        Embarazo embarazo = Embarazo.builder()
                .emailUsuario(request.getEmailUsuario())
                .fechaInicio(request.getFechaInicio())
                .fechaPartoEstimada(fechaParto)
                .semanaActual(semanas)
                .sintomas(request.getSintomas())
                .build();

        repository.save(embarazo);

        kafka.enviarEvento(
                EmbarazoRegistradoEvent.builder()
                        .emailUsuario(request.getEmailUsuario())
                        .mensaje("🤰 Registro de embarazo exitoso. ¡Bienvenida a esta nueva etapa!")
                        .build()
        );
    }
}