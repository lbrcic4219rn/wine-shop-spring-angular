package com.example.fpisfondrum.controller;

import com.example.fpisfondrum.response.PageResponse;
import com.example.fpisfondrum.response.WineResponse;
import com.example.fpisfondrum.service.wine.WineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wine")
@RequiredArgsConstructor
public class WineController {

    private final WineService wineService;

    @GetMapping
    public PageResponse<WineResponse> search(
            @RequestParam(required = false) List<Long> varietyIds,
            @RequestParam(required = false) List<Long> styleIds,
            @PageableDefault(size = 12, sort = "id") Pageable pageable
    ) {
        return PageResponse.from(wineService.searchWines(varietyIds, styleIds, pageable), WineResponse::from);
    }
}
