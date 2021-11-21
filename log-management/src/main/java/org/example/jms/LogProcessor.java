package org.example.jms;

import org.example.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class LogProcessor {

    private static final Logger logger = LoggerFactory.getLogger(LogProcessor.class);

    @JmsListener(destination = "orders.accepted.quantity")
    public void logAcceptedOrdersCountable(Order order) {
        logger.info("Order(countable) with id: {} was accepted. Count of items: {}", order.getId(), order.getQuantity());
    }

    @JmsListener(destination = "orders.accepted.volume")
    public void logAcceptedOrdersLiquids(Order order) {
        logger.info("Order(liquids) with id: {} was accepted. Volume: {}", order.getId(), order.getQuantity());
    }

    @JmsListener(destination = "orders.rejected.quantity")
    public void logRejectedOrdersCountable(Order order) {
        logger.info("Order(countable) with id: {} was rejected. Count of items: {}", order.getId(), order.getQuantity());
    }

    @JmsListener(destination = "orders.rejected.volume")
    public void logRejectedOrdersLiquids(Order order) {
        logger.info("Order(liquids) with id: {} was rejected. Volume: {}", order.getId(), order.getQuantity());
    }
}
