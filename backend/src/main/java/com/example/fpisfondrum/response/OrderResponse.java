package com.example.fpisfondrum.response;

import com.example.fpisfondrum.model.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        String customerName,
        String customerEmail,
        Instant createdAt,
        BigDecimal total,
        List<OrderItemResponse> items
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getCreatedAt(),
                order.getTotal(),
                order.getWines().stream().map(OrderItemResponse::from).toList()
        );
    }
}
