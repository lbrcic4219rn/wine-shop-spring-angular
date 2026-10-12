package com.example.fpisfondrum.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotEmpty List<@Valid @NotNull OrderItemRequest> items
) {
}
