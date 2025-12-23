package org.yam.springbootlibrarycrud.service;

import org.yam.springbootlibrarycrud.dto.OrderDtoRequest;
import org.yam.springbootlibrarycrud.dto.OrderDtoResponse;

public interface OrderService {
    OrderDtoResponse createOrder(OrderDtoRequest body, Long  bookId);

//    Page<Order> getOrders(int page, int size);
//
//    Order updateOrder(Order body, Long id);
//
//    void deleteOrder(Long id);
}
