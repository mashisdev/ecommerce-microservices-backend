package com.microservice.product.mapper;

import com.microservice.product.dto.ProductDto;
import com.microservice.product.dto.request.CreateProductRequest;
import com.microservice.product.dto.request.UpdateProductRequest;
import com.microservice.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    ProductDto toDto(Product product);

    Product toEntity(CreateProductRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(UpdateProductRequest request, @MappingTarget Product product);

}