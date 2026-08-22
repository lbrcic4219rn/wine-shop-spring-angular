package com.example.fpisfondrum.controller;

import com.example.fpisfondrum.request.OrderRequest;
import com.example.fpisfondrum.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("order")
    public ResponseEntity<Void> createOrder(@RequestBody List<OrderRequest> order) {
        orderService.save(order);
        return ResponseEntity.ok().build();
    }
}
