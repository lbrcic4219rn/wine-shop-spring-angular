package com.example.fpisfondrum.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderWineId implements Serializable {
    private Long orderId;
    private Long wineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderWineId that)) return false;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(wineId, that.wineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, wineId);
    }
}
