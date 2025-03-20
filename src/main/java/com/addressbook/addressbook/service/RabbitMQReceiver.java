package com.addressbook.addressbook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQReceiver {

    @RabbitListener(queues = "addressbook.queue")
    public void receiveMessage(String message) {
        log.info("Received message from RabbitMQ: {}", message);
    }
}
