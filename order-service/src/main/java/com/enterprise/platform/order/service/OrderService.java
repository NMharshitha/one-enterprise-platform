package com.enterprise.platform.order.service;

import com.enterprise.platform.order.model.Order;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private final Map<Long, Order> orders = Map.of(
        5001L, new Order(5001L, 1001L, "CREATED"),
        5002L, new Order(5002L, 1002L, "PROCESSING"),
        5003L, new Order(5003L, 1003L, "COMPLETED")
    );

    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }
}
