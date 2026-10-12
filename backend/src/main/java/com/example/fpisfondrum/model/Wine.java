package com.example.fpisfondrum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "wine")
@Getter
@Setter
@NoArgsConstructor
public class Wine {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String imageLocation;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "wine_style_id")
    private WineStyle style;

    @ManyToOne
    @JoinColumn(name = "wine_variety_id")
    private WineVariety variety;

    public Wine(String name, WineStyle style, WineVariety variety, String imageLocation, BigDecimal price) {
        this.imageLocation = imageLocation;
        this.price = price;
        this.name = name;
        this.style = style;
        this.variety = variety;
    }
}
