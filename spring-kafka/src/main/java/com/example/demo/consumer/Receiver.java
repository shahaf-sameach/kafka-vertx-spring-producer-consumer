package com.example.demo.consumer;

import com.example.model.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver {

    @KafkaListener(topics = "${kafka.topic}")
    public void receive(Payload payload) {
        log.info("received payload=" + payload);
    }
}
