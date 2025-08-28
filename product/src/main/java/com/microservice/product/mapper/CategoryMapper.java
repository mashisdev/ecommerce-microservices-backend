package com.microservice.product.mapper;

import com.microservice.product.dto.CategoryDto;
import com.microservice.product.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
}
