package com.example.fpisfondrum.response;

import com.example.fpisfondrum.model.WineStyle;

public record WineStyleResponse(Long id, String name) {
    public static WineStyleResponse from(WineStyle style) {
        return new WineStyleResponse(style.getId(), style.getName());
    }
}
