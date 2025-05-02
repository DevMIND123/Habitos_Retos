package com.retochimba.habitos.habitos_retos.kafka;

import com.retochimba.habitos.habitos_retos.kafka.event.CicloRegistradoEvent;
import com.retochimba.habitos.habitos_retos.kafka.event.EmbarazoRegistradoEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, CicloRegistradoEvent> kafkaTemplateCiclo;
    private final KafkaTemplate<String, EmbarazoRegistradoEvent> kafkaTemplateEmbarazo;


    public void enviarEvento(CicloRegistradoEvent event) {
        kafkaTemplateCiclo.send("ciclo-registrado", event);
        System.out.println("📤 Evento Kafka enviado (ciclo): " + event);
    }

    public void enviarEvento(EmbarazoRegistradoEvent event) {
        kafkaTemplateEmbarazo.send("embarazo-registrado", event);
        System.out.println("📤 Evento Kafka enviado (embarazo): " + event);
    }
}