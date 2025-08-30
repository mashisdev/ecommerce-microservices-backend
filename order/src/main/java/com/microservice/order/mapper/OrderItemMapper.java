package com.microservice.order.mapper;

import com.microservice.order.dto.OrderItemDto;
import com.microservice.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);
}
