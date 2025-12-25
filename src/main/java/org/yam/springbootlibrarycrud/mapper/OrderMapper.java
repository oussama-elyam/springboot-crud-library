package org.yam.springbootlibrarycrud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.yam.springbootlibrarycrud.dto.BookDto;
import org.yam.springbootlibrarycrud.dto.OrderDtoResponse;
import org.yam.springbootlibrarycrud.dto.OrderItemDtoResponse;
import org.yam.springbootlibrarycrud.entitie.Book;
import org.yam.springbootlibrarycrud.entitie.Order;

import java.util.List;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Manual mapping for response DTO
    public OrderDtoResponse toResponseDto(Order order) {
        if (order == null) return null;

        OrderDtoResponse dto = new OrderDtoResponse();
        dto.setId(order.getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDtoResponse> items = order.getItems().stream().map(item -> {
            OrderItemDtoResponse itemDto = new OrderItemDtoResponse();
            itemDto.setId(item.getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setBookPrice(item.getBookPrice());
            itemDto.setDiscount(item.getDiscount());
            itemDto.setPriceTotal(item.getPriceTotal());

            Book book = item.getBook();
            if (book != null) {
                BookDto bookDto = new BookDto();
                bookDto.setId(book.getId());
                bookDto.setName(book.getName());
                bookDto.setPrice(book.getPrice());
                bookDto.setQte(book.getQte());
                bookDto.setStatusBook(book.getStatusBook());
                itemDto.setBook(bookDto);
            }
            return itemDto;
        }).toList();

        dto.setItems(items);

        return dto;
    }
}
