package com.example.demo.consumer;

import com.example.demo.data.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class Receiver {

    @KafkaListener(topics = "${kafka.topic}")
    public void receive(Payload payload) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("received payload=" + payload);
    }
}
