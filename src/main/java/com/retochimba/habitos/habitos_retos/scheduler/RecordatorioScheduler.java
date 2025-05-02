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

    @Scheduled(fixedRate = 60000) // ⏱️ cada 60 segundos (1 minuto)
    public void revisarFechasImportantes() {
        System.out.println("⏱️ Ejecutando tarea programada...");
        LocalDate hoy = LocalDate.now();

        List<CicloMenstrual> ciclos = repository.findAll();

        for (CicloMenstrual ciclo : ciclos) {
            long diasParaMenstruacion = hoy.until(ciclo.getFechaProximaMenstruacion()).getDays();
            long diasParaOvulacion = hoy.until(ciclo.getFechaOvulacion()).getDays();

            System.out.println("🔎 Revisión de ciclo:");
            System.out.println("📧 Usuario: " + ciclo.getEmailUsuario());
            System.out.println("📅 Próxima menstruación: " + ciclo.getFechaProximaMenstruacion());
            System.out.println("📅 Ovulación: " + ciclo.getFechaOvulacion());
            System.out.println("📏 Días para menstruación: " + diasParaMenstruacion);
            System.out.println("📏 Días para ovulación: " + diasParaOvulacion);

            // ✅ Menstruación próxima (evita notificar si ya es hoy)
            if (diasParaMenstruacion > 0 && diasParaMenstruacion <= 3) {
                String mensaje = "🩸 Tu menstruación llegará en " + diasParaMenstruacion + " días.";
                System.out.println("📨 Evento Kafka enviado: CicloRegistradoEvent(emailUsuario=" + ciclo.getEmailUsuario() + ", mensaje=" + mensaje + ")");
                kafkaProducer.enviarEvento(
                    new CicloRegistradoEvent(
                        ciclo.getEmailUsuario(),
                        mensaje
                    )
                );
            }

            // ✅ Ovulación próxima (evita notificar si ya es hoy)
            if (diasParaOvulacion > 0 && diasParaOvulacion <= 3) {
                String mensaje = "🌸 Se aproxima tu ovulación en " + diasParaOvulacion + " días.";
                System.out.println("📨 Evento Kafka enviado: CicloRegistradoEvent(emailUsuario=" + ciclo.getEmailUsuario() + ", mensaje=" + mensaje + ")");
                kafkaProducer.enviarEvento(
                    new CicloRegistradoEvent(
                        ciclo.getEmailUsuario(),
                        mensaje
                    )
                );
            }
        }
    }
}
