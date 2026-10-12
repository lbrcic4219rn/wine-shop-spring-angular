package com.example.fpisfondrum.response;

import com.example.fpisfondrum.model.Wine;

import java.math.BigDecimal;

public record WineResponse(
        Long id,
        String name,
        BigDecimal price,
        String imageLocation,
        WineStyleResponse style,
        WineVarietyResponse variety
) {
    public static WineResponse from(Wine wine) {
        return new WineResponse(
                wine.getId(),
                wine.getName(),
                wine.getPrice(),
                wine.getImageLocation(),
                WineStyleResponse.from(wine.getStyle()),
                WineVarietyResponse.from(wine.getVariety())
        );
    }
}
