package com.microservice.product.service.impl;

import com.microservice.product.dto.CategoryDto;
import com.microservice.product.dto.request.CategoryRequest;
import com.microservice.product.entity.Category;
import com.microservice.product.mapper.CategoryMapper;
import com.microservice.product.repository.CategoryRepository;
import com.microservice.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Mono<CategoryDto> createCategory(CategoryRequest request) {
        Category newCategory = new Category();
        newCategory.setName(request.name());
        return categoryRepository.save(newCategory)
                .map(categoryMapper::toDto);
    }

    public Flux<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .map(categoryMapper::toDto);
    }

    public Mono<CategoryDto> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto);
    }

    public Mono<CategoryDto> updateCategory(Long id, CategoryRequest request) {
        return categoryRepository.findById(id)
                .flatMap(category -> {
                    category.setName(request.name());
                    return categoryRepository.save(category);
                })
                .map(categoryMapper::toDto);
    }

    public Mono<Void> deleteCategory(Long id) {
        return categoryRepository.deleteById(id);
    }
}