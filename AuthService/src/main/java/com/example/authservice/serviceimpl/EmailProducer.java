package com.example.authservice.serviceimpl;

import com.example.authservice.config.RabbitMQConfig;
import com.example.authservice.dto.EmailRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig config;

    public void sendEmailRequest(EmailRequestDTO request) {
        log.info("Sending email to queue: {}", request);
        rabbitTemplate.convertAndSend(
                config.getExchange(),
                config.getRoutingKey(),
                request
        );
        log.info("Email message sent to queue successfully");
    }
}
