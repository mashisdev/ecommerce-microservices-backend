package com.microservice.product.service;

import com.microservice.product.dto.ProductDto;
import com.microservice.product.dto.request.CreateProductRequest;
import com.microservice.product.dto.request.UpdateProductRequest;
import com.microservice.product.entity.Product;
import com.microservice.product.mapper.ProductMapper;
import com.microservice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public Mono<ProductDto> createProduct(CreateProductRequest request) {
        Product newProduct = productMapper.toEntity(request);
        newProduct.setActive(true);

        return productRepository.save(newProduct)
                .map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<ProductDto> getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<ProductDto> updateProduct(Long productId, UpdateProductRequest request) {
        return productRepository.findById(productId)
                .flatMap(product -> {
                    productMapper.updateEntityFromDto(request, product);
                    return productRepository.save(product);
                })
                .map(productMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<Void> deleteProduct(Long productId) {
        return productRepository.deleteById(productId);
    }
}
