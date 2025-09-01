package com.microservice.product.service;

import com.microservice.product.dto.ProductDto;
import com.microservice.product.dto.request.CreateProductRequest;
import com.microservice.product.dto.request.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDto> createProduct(CreateProductRequest request);
    Mono<ProductDto> getProductById(Long productId);
    Mono<ProductDto> getProductBySku(String sku);
    Mono<Page<ProductDto>> getAllProducts(Pageable pageable);
    Mono<ProductDto> updateProduct(Long productId, UpdateProductRequest request);
    Mono<Void> deleteProduct(Long productId);
}
