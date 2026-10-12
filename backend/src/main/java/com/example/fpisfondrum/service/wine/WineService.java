package com.example.fpisfondrum.service.wine;

import com.example.fpisfondrum.model.Wine;

import java.util.List;

public interface WineService {
    List<Wine> findAll();
    Wine findById(Long id);
    void save(Wine wine);
    List<Wine> searchWines(List<Long> wineVarietyIds, List<Long> wineStyleIds);

}
