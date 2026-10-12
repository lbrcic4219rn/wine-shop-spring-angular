package com.example.fpisfondrum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "wine_variety")
@Getter
@Setter
@NoArgsConstructor
public class WineVariety {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    public WineVariety(String name) {
        this.name = name;
    }
}
