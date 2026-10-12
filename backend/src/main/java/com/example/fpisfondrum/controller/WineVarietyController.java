package com.example.fpisfondrum.controller;

import com.example.fpisfondrum.response.WineVarietyResponse;
import com.example.fpisfondrum.service.winevariety.WineVarietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wine-variety")
@RequiredArgsConstructor
public class WineVarietyController {
    private final WineVarietyService wineVarietyService;

    @GetMapping
    public List<WineVarietyResponse> getAll() {
        return wineVarietyService.findAll().stream().map(WineVarietyResponse::from).toList();
    }
}
