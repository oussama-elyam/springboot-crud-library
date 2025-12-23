package org.yam.springbootlibrarycrud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootlibrarycrud.dto.OrderDtoRequest;
import org.yam.springbootlibrarycrud.dto.OrderDtoResponse;
import org.yam.springbootlibrarycrud.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDtoResponse> createOrder(@Valid @RequestBody OrderDtoRequest request) {

        OrderDtoResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/paggination")
    public ResponseEntity<List<OrderDtoResponse>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<OrderDtoResponse> Orders = orderService.getOrders(page, size);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Orders.getContent());
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
//        orderService.deleteOrder(id);
//        return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully");
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderDtoRequest> updateOrder(@RequestBody OrderDtoRequest body, @PathVariable("id") Long id) {
//
//        OrderDtoRequest updatedOrder = orderService.updateOrder(body, id);
//        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
//    }
}
