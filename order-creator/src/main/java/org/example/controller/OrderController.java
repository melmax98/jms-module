package org.example.controller;


import org.example.jms.Sender;
import org.example.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final Sender sender;

    public OrderController(Sender sender) {
        this.sender = sender;
    }


    @PostMapping("/order")
    public void createOrder(@RequestBody Order order) {
        sender.send("orders", order);
    }
}
