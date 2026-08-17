package com.enterprise.platform.order.dto;

public record OrderResponse(
    Long orderId, Long userId, String status, String userName, String userEmail
) {}
