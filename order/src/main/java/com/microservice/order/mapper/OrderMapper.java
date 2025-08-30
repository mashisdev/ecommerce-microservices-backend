package com.microservice.order.mapper;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "orderItems", source = "orderItems")
    OrderDto toDto(Order order);
}
