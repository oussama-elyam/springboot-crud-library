package org.yam.springbootlibrarycrud.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yam.springbootlibrarycrud.dto.OrderDtoRequest;
import org.yam.springbootlibrarycrud.dto.OrderDtoResponse;
import org.yam.springbootlibrarycrud.dto.OrderItemDtoRequest;
import org.yam.springbootlibrarycrud.mapper.OrderMapper;
import org.yam.springbootlibrarycrud.model.Book;
import org.yam.springbootlibrarycrud.model.Order;
import org.yam.springbootlibrarycrud.model.OrderItem;
import org.yam.springbootlibrarycrud.model.enums.StatusBook;
import org.yam.springbootlibrarycrud.repository.BookRepository;
import org.yam.springbootlibrarycrud.repository.OrderRepository;
import org.yam.springbootlibrarycrud.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    @Transactional
    public OrderDtoResponse createOrder(OrderDtoRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one book");
        }

        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .build();

        long totalPrice = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDtoRequest itemRequest : request.getItems()) {
            Book book = bookRepository.findById(itemRequest.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found: " + itemRequest.getBookId()));

            if (itemRequest.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be positive for book: " + book.getName());
            }

            if (book.getQte() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for book: " + book.getName());
            }

            long itemPrice = book.getPrice() * itemRequest.getQuantity();

            // Create order item
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .book(book)
                    .quantity(itemRequest.getQuantity())
                    .bookPrice(book.getPrice()) // snapshot
                    .discount(itemRequest.getDiscount() != null ? itemRequest.getDiscount() : 0)
                    .priceTotal(itemPrice - (long)(itemPrice * (itemRequest.getDiscount() != null ? itemRequest.getDiscount() : 0)))
                    .build();

            orderItems.add(orderItem);

            // Update book stock
            book.setQte(book.getQte() - itemRequest.getQuantity());
            if (book.getQte() == 0) {
                book.setStatusBook(StatusBook.NOTAVAILABLE);
            }
            bookRepository.save(book);

            totalPrice += orderItem.getPriceTotal();
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponseDto(savedOrder);
    }



    @Override
    public Page<OrderDtoResponse> getOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable).map(orderMapper::toResponseDto);
    }
//
//    @Override
//    @Transactional
//    public OrderDtoRequest updateOrder(OrderDtoRequest bodyRequest, Long id) {
//        Order existingOrder = orderRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
//
//        existingOrder.setName(bodyRequest.getName());
//        existingOrder.setPrice(bodyRequest.getPrice());
//        existingOrder.setQte(bodyRequest.getQte());
//        existingOrder.setStatusOrder(resolveStatus(bodyRequest.getQte()));
//
//        return orderMapper.toResponseDto(existingOrder); // no explicit save() needed, managed entity
//    }
//
//    @Transactional
//    @Override
//    public void deleteOrder(Long id) {
//
//        Order existingOrder = orderRepository.findById(id)
//                .orElseThrow(() ->
//                        new EntityNotFoundException("Order with ID " + id + " not found")
//                );
//
//        orderRepository.delete(existingOrder);
//    }
//
//    private StatusOrder resolveStatus(Long qte) {
//        return qte > 0 ? StatusOrder.AVAILABLE : StatusOrder.NOTAVAILABLE;
//    }
}
