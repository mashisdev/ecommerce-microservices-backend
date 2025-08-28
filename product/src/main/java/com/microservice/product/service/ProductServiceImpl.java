package com.microservice.product.service;

import com.microservice.product.dto.ProductDto;
import com.microservice.product.dto.request.CreateProductRequest;
import com.microservice.product.dto.request.UpdateProductRequest;
import com.microservice.product.entity.Product;
import com.microservice.product.mapper.ProductMapper;
import com.microservice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @Transactional(readOnly = true)
    @Override
    public Mono<Page<ProductDto>> getAllProducts(Pageable pageable) {
        Flux<ProductDto> productsFlux = productRepository.findAll()
                .skip(pageable.getOffset())
                .take(pageable.getPageSize())
                .map(productMapper::toDto);

        Mono<Long> countMono = productRepository.count();

        return Mono.zip(productsFlux.collectList(), countMono)
                .map(tuple -> {
                    List<ProductDto> products = tuple.getT1();
                    Long totalCount = tuple.getT2();
                    return new PageImpl<>(products, pageable, totalCount);
                });
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
