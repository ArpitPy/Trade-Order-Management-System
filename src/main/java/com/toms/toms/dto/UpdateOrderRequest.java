package com.toms.toms.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateOrderRequest {
    @NotNull(message = "Quantity is required")
    @Min(value=1, message="Quantity must be at least  1")
    private Integer quantity;

    @NotNull(message = "Price is required")
    @DecimalMin(value="0.01", message = "Price must be greater than 0")
    private Double price;
}
