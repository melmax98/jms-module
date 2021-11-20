package org.example.jms;

import model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @JmsListener(destination = "orders")
    public void receiveOrder(Order order) {
        logger.info("Order received: " + order);
    }
}
