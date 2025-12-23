package org.yam.springbootlibrarycrud.dto;

import lombok.Data;

@Data
public class OrderItemDtoResponse {
    private Long id;
    private BookDto book;  // snapshot info of book
    private Integer quantity;
    private Long bookPrice;
    private Double discount;
    private Long priceTotal;
}
