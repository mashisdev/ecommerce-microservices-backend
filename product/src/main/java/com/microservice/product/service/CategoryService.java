package com.microservice.product.service;

import com.microservice.product.dto.CategoryDto;
import com.microservice.product.dto.request.CategoryRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Mono<CategoryDto> createCategory(CategoryRequest request);
    Mono<CategoryDto> getCategoryById(Long id);
    Flux<CategoryDto> getAllCategories();
    Mono<CategoryDto> updateCategory(Long id, CategoryRequest request);
    Mono<Void> deleteCategory(Long id);
}
