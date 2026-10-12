package com.example.fpisfondrum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wine_order")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String customerName;

    @NotBlank
    @Email
    private String customerEmail;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderWine> wines = new ArrayList<>();

    public Order(String customerName, String customerEmail) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public void addWine(Wine wine, int quantity) {
        OrderWine orderWine = new OrderWine(this, wine, quantity);
        wines.add(orderWine);
        total = total.add(orderWine.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
    }

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }
}
