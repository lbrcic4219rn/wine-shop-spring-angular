package com.example.fpisfondrum.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {
    private Long wineId;
    private int quantity;
}
