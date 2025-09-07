package com.example.emailservice.service;

import com.example.emailservice.dto.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailConsumer {

    @Autowired
    private EmailService emailSenderService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleEmailRequest(EmailRequest request) {
        log.info("Received email request from queue: {}", request);
        emailSenderService.sendEmail(request);
        log.info("Email sent successfully to: {}", request.getTo());
    }
}
