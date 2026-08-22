package com.example.fpisfondrum.service.order;

import com.example.fpisfondrum.model.Order;
import com.example.fpisfondrum.model.OrderWine;
import com.example.fpisfondrum.model.Wine;
import com.example.fpisfondrum.repo.OrderRepo;
import com.example.fpisfondrum.request.OrderRequest;
import com.example.fpisfondrum.service.wine.WineServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final WineServiceImpl wineService;

    @Override
    @Transactional
    public void save(List<OrderRequest> order) {
        Order created = new Order();
        created.setWines(new ArrayList<>());

        order.forEach(req -> {
            Wine wine = wineService.findById(req.getWineId());
            OrderWine orderWine = new OrderWine(created, wine, req.getQuantity());
            created.getWines().add(orderWine);
        });

        orderRepo.save(created);
    }
}
