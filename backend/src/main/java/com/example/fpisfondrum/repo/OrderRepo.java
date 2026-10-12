package com.example.fpisfondrum.repo;

import com.example.fpisfondrum.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
