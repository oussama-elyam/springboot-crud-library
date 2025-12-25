package org.yam.springbootlibrarycrud.service;

import org.springframework.data.domain.Page;
import org.yam.springbootlibrarycrud.dto.OrderDtoRequest;
import org.yam.springbootlibrarycrud.dto.OrderDtoResponse;

public interface OrderService {
    OrderDtoResponse createOrder(OrderDtoRequest body);

    Page<OrderDtoResponse> getOrders(int page, int size);
//
//    Order updateOrder(Order body, Long id);
//
    void deleteOrder(Long id);
}
