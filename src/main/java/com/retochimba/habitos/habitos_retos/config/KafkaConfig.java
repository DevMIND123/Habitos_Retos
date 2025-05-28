package com.retochimba.habitos.habitos_retos.config;

import com.retochimba.habitos.habitos_retos.kafka.event.CicloRegistradoEvent;
import com.retochimba.habitos.habitos_retos.kafka.event.EmbarazoRegistradoEvent;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, CicloRegistradoEvent> cicloProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, CicloRegistradoEvent> kafkaTemplateCiclo() {
        return new KafkaTemplate<>(cicloProducerFactory());
    }

    @Bean
    public ProducerFactory<String, EmbarazoRegistradoEvent> embarazoProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, EmbarazoRegistradoEvent> kafkaTemplateEmbarazo() {
        return new KafkaTemplate<>(embarazoProducerFactory());
    }
}
