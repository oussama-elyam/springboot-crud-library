package org.yam.springbootlibrarycrud.dto;

import lombok.Data;

@Data
public class OrderDtoResponse {
    private Long bookId;
    private String orderName;
    private Long priceTotal;
    private Integer qteTotal;
}