package org.yam.springbootlibrarycrud.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDtoResponse {
    private Long id;
    private Long totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemDtoResponse> items;
}