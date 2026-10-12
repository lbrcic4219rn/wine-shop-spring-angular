package com.example.fpisfondrum.controller;

import com.example.fpisfondrum.model.WineStyle;
import com.example.fpisfondrum.service.winestyle.WineStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wine-style")
@RequiredArgsConstructor
public class WineStyleController {
    private final WineStyleService wineStyleService;

    @GetMapping
    public List<WineStyle> getAll() {
        return wineStyleService.findAll();
    }
}
