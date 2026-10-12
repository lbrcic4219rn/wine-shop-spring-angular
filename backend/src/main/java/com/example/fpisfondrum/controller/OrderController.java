package com.example.fpisfondrum.controller;

import com.example.fpisfondrum.request.OrderRequest;
import com.example.fpisfondrum.response.OrderResponse;
import com.example.fpisfondrum.response.PageResponse;
import com.example.fpisfondrum.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest order) {
        return OrderResponse.from(orderService.save(order));
    }

    @GetMapping("order")
    public PageResponse<OrderResponse> getAll(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return PageResponse.from(orderService.findAll(pageable), OrderResponse::from);
    }

    @GetMapping("order/{id}")
    public OrderResponse getById(@PathVariable Long id) {
        return OrderResponse.from(orderService.findById(id));
    }
}
