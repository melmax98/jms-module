package org.example.jms;

import org.example.model.ItemType;
import org.example.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Processor {

    @Value("${threshold.volume}")
    private int volumeThreshold;
    @Value("${threshold.quantity}")
    private int quantityThreshold;

    private int orderedVolume;

    private final Sender sender;

    public Processor(Sender sender) {
        this.sender = sender;
    }

    @JmsListener(destination = "orders")
    public void receiveOrder(Order order) {
        if (ItemType.COUNTABLE_ITEM.equals(order.getItemType())) {
            if (order.getQuantity() <= quantityThreshold) {
                sender.send("orders.accepted.quantity", order);
                return;
            }
            sender.send("orders.rejected.quantity", order);
        } else {
            if (order.getQuantity() + orderedVolume <= volumeThreshold) {
                orderedVolume += order.getQuantity();
                sender.send("orders.accepted.volume", order);
                return;
            }
            sender.send("orders.rejected.volume", order);
        }
    }
}
