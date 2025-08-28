package com.microservice.product.mapper;

import com.microservice.product.dto.BrandDto;
import com.microservice.product.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDto toDto(Brand brand);
    Brand toEntity(BrandDto brandDto);
}
