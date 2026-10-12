package com.example.fpisfondrum.service.wine;

import com.example.fpisfondrum.model.Wine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WineService {
    List<Wine> findAll();

    Wine findById(Long id);

    void save(Wine wine);

    Page<Wine> searchWines(List<Long> wineVarietyIds, List<Long> wineStyleIds, Pageable pageable);

}
