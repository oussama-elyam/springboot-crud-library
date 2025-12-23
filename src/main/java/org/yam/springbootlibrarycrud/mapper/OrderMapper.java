package org.yam.springbootlibrarycrud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.yam.springbootlibrarycrud.dto.OrderDtoRequest;
import org.yam.springbootlibrarycrud.dto.OrderDtoResponse;
import org.yam.springbootlibrarycrud.model.Order;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order RequesttoEntity(OrderDtoRequest dto) {
        return modelMapper.map(dto, Order.class);
    }

    public Order RequesttoEntity(OrderDtoResponse dto) {
        return modelMapper.map(dto, Order.class);
    }

    public OrderDtoRequest toRequestDto(Order entity) {
        return modelMapper.map(entity, OrderDtoRequest.class);
    }

    public OrderDtoResponse toResponseDto(Order entity) {
        return modelMapper.map(entity, OrderDtoResponse.class);
    }

}

