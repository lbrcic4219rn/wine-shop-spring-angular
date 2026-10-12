package com.example.fpisfondrum.response;

import com.example.fpisfondrum.model.WineVariety;

public record WineVarietyResponse(Long id, String name) {
    public static WineVarietyResponse from(WineVariety variety) {
        return new WineVarietyResponse(variety.getId(), variety.getName());
    }
}
