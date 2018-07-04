package com.example.demo.consumer;

import java.util.concurrent.CountDownLatch;

import com.example.demo.data.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Receiver {


    @KafkaListener(topics = "${kafka.topic}")
    public void receive(Payload payload) {
        log.info("received payload=" + payload);
    }
}
