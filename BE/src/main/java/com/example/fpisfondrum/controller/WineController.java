package com.example.fpisfondrum.controller;

import com.example.fpisfondrum.model.Wine;
import com.example.fpisfondrum.request.WineSearchRequest;
import com.example.fpisfondrum.service.wine.WineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wine")
@RequiredArgsConstructor
public class WineController {

    private final WineService wineService;

    @PostMapping("search")
    public List<Wine> search(@RequestBody WineSearchRequest request) {
        return wineService.searchWines(request.getWineVarietyIds(), request.getWineStyleIds());
    }
}
