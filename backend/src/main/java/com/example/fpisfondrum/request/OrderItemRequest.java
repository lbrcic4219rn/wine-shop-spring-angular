package com.example.fpisfondrum.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull Long wineId,
        @Min(1) @Max(1000) int quantity
) {
}
