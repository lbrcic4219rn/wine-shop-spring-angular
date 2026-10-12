package com.example.fpisfondrum.service.order;

import com.example.fpisfondrum.exception.NotFoundException;
import com.example.fpisfondrum.model.Order;
import com.example.fpisfondrum.repo.OrderRepo;
import com.example.fpisfondrum.request.OrderRequest;
import com.example.fpisfondrum.service.wine.WineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final WineService wineService;

    @Override
    @Transactional
    public Order save(OrderRequest request) {
        // The same wine may appear more than once in a request; an order holds one line per wine
        Map<Long, Integer> quantities = new LinkedHashMap<>();
        request.items().forEach(item -> quantities.merge(item.wineId(), item.quantity(), Integer::sum));

        Order created = new Order(request.customerName(), request.customerEmail());
        quantities.forEach((wineId, quantity) -> created.addWine(wineService.findById(wineId), quantity));

        return orderRepo.save(created);
    }

    @Override
    public Order findById(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }
}
