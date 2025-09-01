package com.microservice.order.mapper;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.dto.OrderItemDto;
import com.microservice.order.entity.Order;
import com.microservice.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "orderItems", source = "orderItems")
    OrderDto toDto(Order order);

    @Mapping(target = "orderItems", source = "orderItems")
    Order toEntity(OrderDto orderDto);

    Set<OrderItem> toOrderItemSet(List<OrderItemDto> orderItemDtos);

    List<OrderItemDto> toOrderItemDtoList(Set<OrderItem> orderItems);
}
