package com.example.fpisfondrum.service.order;

import com.example.fpisfondrum.request.OrderRequest;

import java.util.List;

public interface OrderService {
    void save(List<OrderRequest> order);
}
