package com.example.WebSocketAndKafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String payload) {
        log.info("Producer TOPIC : " + topic);
        log.info("Producer PAYLOAD : " + payload);
        ListenableFuture<SendResult<String, String>> listenable = kafkaTemplate.send(topic, payload);
    }
}