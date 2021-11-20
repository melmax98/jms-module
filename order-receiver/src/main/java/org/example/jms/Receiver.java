package org.example.jms;

import model.ItemType;
import model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Value("${threshold.volume}")
    private int volumeThreshold;
    @Value("${threshold.quantity}")
    private int quantityThreshold;

    private int orderedVolume;

    private final JmsTemplate jmsTemplate;

    public Receiver(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "orders")
    public void receiveOrder(Order order) {
        if (ItemType.COUNTABLE_ITEM.equals(order.getItemType())) {
            if (order.getQuantity() <= quantityThreshold) {
                jmsTemplate.convertAndSend("orders.accepted.quantity", order);
                return;
            }
            jmsTemplate.convertAndSend("orders.rejected.quantity", order);
        } else {
            if (order.getQuantity() + orderedVolume <= volumeThreshold) {
                orderedVolume += order.getQuantity();
                jmsTemplate.convertAndSend("orders.accepted.quantity", order);
                return;
            }
            jmsTemplate.convertAndSend("orders.rejected.volume", order);
        }
    }
}
