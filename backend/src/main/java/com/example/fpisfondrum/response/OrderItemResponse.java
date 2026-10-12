package com.example.fpisfondrum.response;

import com.example.fpisfondrum.model.OrderWine;

import java.math.BigDecimal;

public record OrderItemResponse(Long wineId, String wineName, int quantity, BigDecimal unitPrice) {
    public static OrderItemResponse from(OrderWine orderWine) {
        return new OrderItemResponse(
                orderWine.getWine().getId(),
                orderWine.getWine().getName(),
                orderWine.getQuantity(),
                orderWine.getUnitPrice()
        );
    }
}
