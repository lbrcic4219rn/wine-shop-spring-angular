package com.example.fpisfondrum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wine_style")
@Getter
@Setter
@NoArgsConstructor
public class WineStyle {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    public WineStyle(String name) {
        this.name = name;
    }
}
