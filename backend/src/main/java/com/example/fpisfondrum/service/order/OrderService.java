package com.example.fpisfondrum.service.order;

import com.example.fpisfondrum.model.Order;
import com.example.fpisfondrum.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Order save(OrderRequest request);

    Order findById(Long id);

    Page<Order> findAll(Pageable pageable);
}
