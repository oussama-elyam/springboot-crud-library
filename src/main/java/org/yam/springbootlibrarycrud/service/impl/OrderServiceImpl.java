package org.yam.springbootlibrarycrud.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yam.springbootlibrarycrud.dto.OrderDtoRequest;
import org.yam.springbootlibrarycrud.dto.OrderDtoResponse;
import org.yam.springbootlibrarycrud.mapper.OrderMapper;
import org.yam.springbootlibrarycrud.model.Book;
import org.yam.springbootlibrarycrud.model.Order;
import org.yam.springbootlibrarycrud.model.enums.StatusBook;
import org.yam.springbootlibrarycrud.repository.BookRepository;
import org.yam.springbootlibrarycrud.repository.OrderRepository;
import org.yam.springbootlibrarycrud.service.OrderService;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderDtoResponse createOrder(OrderDtoRequest request, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (request.getQteTotal() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (book.getQte() < request.getQteTotal()) {
            throw new IllegalArgumentException("Not enough stock");
        }

        long totalPrice = book.getPrice() * request.getQteTotal();

        Order order = Order.builder()
                .orderName(book.getName())
                .qteTotal(request.getQteTotal())
                .priceTotal(totalPrice)
                .book(book)
                .build();

        book.setQte(book.getQte() - request.getQteTotal());
        if (book.getQte() == 0) {
            book.setStatusBook(StatusBook.NOTAVAILABLE);
        }

        bookRepository.save(book);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponseDto(savedOrder);
    }



//    @Override
//    public Page<Order> getOrders(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return orderRepository.findAll(pageable);
//    }
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
