package com.example.fpisfondrum.service.winestyle;

import com.example.fpisfondrum.model.WineStyle;
import com.example.fpisfondrum.repo.WineStyleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WineStyleServiceImpl implements WineStyleService{
    private final WineStyleRepo wineStyleRepo;

    @Override
    public List<WineStyle> findAll() {
        return wineStyleRepo.findAll();
    }

    @Override
    public WineStyle save(WineStyle wineStyle) {
        return wineStyleRepo.save(wineStyle);
    }
}
