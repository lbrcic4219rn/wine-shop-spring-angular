package com.example.fpisfondrum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wine_order_wines")
@Getter
@Setter
@NoArgsConstructor
public class OrderWine {
    @EmbeddedId
    private OrderWineId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("wineId")
    @JoinColumn(name = "wines_id")
    private Wine wine;

    private int quantity;

    public OrderWine(Order order, Wine wine, int quantity) {
        this.order = order;
        this.wine = wine;
        this.quantity = quantity;
        this.id = new OrderWineId(order.getId(), wine.getId());
    }
}
