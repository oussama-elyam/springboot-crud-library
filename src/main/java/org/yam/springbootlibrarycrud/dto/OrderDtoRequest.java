package org.yam.springbootlibrarycrud.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDtoRequest {
    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemDtoRequest> items;
}