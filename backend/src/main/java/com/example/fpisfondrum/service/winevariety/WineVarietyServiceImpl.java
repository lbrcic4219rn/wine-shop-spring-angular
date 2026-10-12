package com.example.fpisfondrum.service.winevariety;

import com.example.fpisfondrum.model.WineVariety;
import com.example.fpisfondrum.repo.WineVarietyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WineVarietyServiceImpl implements WineVarietyService{
    private final WineVarietyRepo wineVarietyRepo;

    @Override
    public List<WineVariety> findAll() {
        return wineVarietyRepo.findAll();
    }

    @Override
    public WineVariety save(WineVariety wineVariety) {
        return wineVarietyRepo.save(wineVariety);
    }
}
