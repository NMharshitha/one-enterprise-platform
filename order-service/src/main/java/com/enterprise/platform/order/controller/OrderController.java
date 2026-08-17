package com.enterprise.platform.order.controller;

import com.enterprise.platform.order.client.UserClient;
import com.enterprise.platform.order.dto.OrderResponse;
import com.enterprise.platform.order.dto.UserResponse;
import com.enterprise.platform.order.model.Order;
import com.enterprise.platform.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserClient userClient;

    public OrderController(OrderService orderService, UserClient userClient) {
        this.orderService = orderService;
        this.userClient = userClient;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse user = userClient.getUser(order.userId());

        return ResponseEntity.ok(new OrderResponse(
            order.orderId(), order.userId(), order.status(),
            user.name(), user.email()
        ));
    }
}
