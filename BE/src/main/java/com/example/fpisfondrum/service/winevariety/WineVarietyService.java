package com.example.fpisfondrum.service.winevariety;

import com.example.fpisfondrum.model.WineVariety;

import java.util.List;

public interface WineVarietyService {
     List<WineVariety> findAll();
     WineVariety save(WineVariety wineVariety);
}

