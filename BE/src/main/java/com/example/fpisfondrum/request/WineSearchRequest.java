package com.example.fpisfondrum.request;

import lombok.Data;

import java.util.List;

@Data
public class WineSearchRequest {
    List<Long> wineVarietyIds;
    List<Long> wineStyleIds;
}
