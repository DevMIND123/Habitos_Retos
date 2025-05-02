package com.retochimba.habitos.habitos_retos.scheduler;

import com.retochimba.habitos.habitos_retos.kafka.KafkaProducerService;
import com.retochimba.habitos.habitos_retos.kafka.event.EmbarazoRegistradoEvent;
import com.retochimba.habitos.habitos_retos.model.Embarazo;
import com.retochimba.habitos.habitos_retos.repository.EmbarazoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmbarazoScheduler {

    private final EmbarazoRepository repository;
    private final KafkaProducerService kafkaProducer;

    @Scheduled(fixedRate = 60000) // cada minuto (temporal para pruebas)
    public void enviarRecordatoriosSemanales() {
        List<Embarazo> embarazos = repository.findAll();

        for (Embarazo embarazo : embarazos) {
            long dias = ChronoUnit.DAYS.between(embarazo.getFechaInicio(), LocalDate.now());
            int semanaActual = (int) (dias / 7);

            // Si está en una nueva semana y no se ha notificado aún
            if (semanaActual >= 1 && !Integer.valueOf(semanaActual).equals(embarazo.getUltimaSemanaNotificada())) {
                String mensaje = "🤰 Estás en la semana " + semanaActual + " de tu embarazo. ¡Cuídate mucho!";
                
                kafkaProducer.enviarEvento(
                        EmbarazoRegistradoEvent.builder()
                                .emailUsuario(embarazo.getEmailUsuario())
                                .mensaje(mensaje)
                                .build()
                );

                // actualizar la semana notificada
                embarazo.setUltimaSemanaNotificada(semanaActual);
                repository.save(embarazo);
            }
        }
    }
}
