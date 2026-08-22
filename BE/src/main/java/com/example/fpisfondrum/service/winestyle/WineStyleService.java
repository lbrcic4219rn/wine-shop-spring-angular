package com.example.fpisfondrum.service.winestyle;

import com.example.fpisfondrum.model.WineStyle;

import java.util.List;

public interface WineStyleService {
    List<WineStyle> findAll();
    WineStyle save(WineStyle wineStyle);
}
