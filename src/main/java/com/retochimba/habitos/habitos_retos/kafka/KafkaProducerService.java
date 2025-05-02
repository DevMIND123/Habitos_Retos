package com.retochimba.habitos.habitos_retos.kafka;

import com.retochimba.habitos.habitos_retos.kafka.event.CicloRegistradoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, CicloRegistradoEvent> kafkaTemplate;

    private final String TOPIC = "ciclo-registrado";

    public void enviarEvento(CicloRegistradoEvent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("📤 Evento Kafka enviado: " + event);
    }
}