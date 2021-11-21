package org.example.jms;

import org.example.model.Order;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Sender {

    private final JmsTemplate jmsTemplate;


    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public void send(String topic, Order order) {
        jmsTemplate.convertAndSend(topic, order);
    }
}
