package com.retochimba.habitos.habitos_retos.scheduler;

import com.retochimba.habitos.habitos_retos.kafka.KafkaProducerService;
import com.retochimba.habitos.habitos_retos.kafka.event.CicloRegistradoEvent;
import com.retochimba.habitos.habitos_retos.model.CicloMenstrual;
import com.retochimba.habitos.habitos_retos.repository.CicloMenstrualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecordatorioScheduler {

    private final CicloMenstrualRepository repository;
    private final KafkaProducerService kafkaProducer;

    @Scheduled(cron = "0 0 7 * * *") // ⏰ todos los días a las 7:00 am
    public void revisarFechasImportantes() {
        LocalDate hoy = LocalDate.now();

        List<CicloMenstrual> ciclos = repository.findAll();

        for (CicloMenstrual ciclo : ciclos) {
            long diasParaMenstruacion = hoy.until(ciclo.getFechaProximaMenstruacion()).getDays();
            long diasParaOvulacion = hoy.until(ciclo.getFechaOvulacion()).getDays();

            // Menstruación próxima
            if (diasParaMenstruacion >= 0 && diasParaMenstruacion <= 3) {
                kafkaProducer.enviarEvento(
                        new CicloRegistradoEvent(
                                ciclo.getEmailUsuario(),
                                "🩸 Tu menstruación llegará en " + diasParaMenstruacion + " días."
                        )
                );
            }

            // Ovulación próxima
            if (diasParaOvulacion >= 0 && diasParaOvulacion <= 3) {
                kafkaProducer.enviarEvento(
                        new CicloRegistradoEvent(
                                ciclo.getEmailUsuario(),
                                "🌸 Se aproxima tu ovulación en " + diasParaOvulacion + " días."
                        )
                );
            }
        }
    }
}
