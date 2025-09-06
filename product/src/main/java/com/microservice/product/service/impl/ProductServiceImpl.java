package com.microservice.product.service.impl;

import com.microservice.product.dto.ProductDto;
import com.microservice.product.exception.ProductNotFoundException;
import com.microservice.product.rabbitmq.message.inventory.ProductMessageRequest;
import com.microservice.product.dto.request.CreateProductRequest;
import com.microservice.product.dto.request.UpdateProductRequest;
import com.microservice.product.entity.Product;
import com.microservice.product.mapper.ProductMapper;
import com.microservice.product.repository.ProductRepository;
import com.microservice.product.service.ProductService;
import com.microservice.product.rabbitmq.RabbitMQJsonProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RabbitMQJsonProducer rabbitMQJsonProducer;

    @Transactional
    @Override
    public Mono<ProductDto> createProduct(CreateProductRequest request) {
        Product newProduct = productMapper.toEntity(request);
        newProduct.setActive(true);

        return productRepository.save(newProduct)
                .map(product -> {
                    ProductDto productDto = productMapper.toDto(product);
                    ProductMessageRequest message = new ProductMessageRequest(
                            product.getSku(),
                            "CREATE",
                            request.quantity()
                    );
                    rabbitMQJsonProducer.sendInventoryMessage(message);
                    return productDto;
                });
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<ProductDto> getProductById(Long productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with ID: " + productId)))
                .map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<ProductDto> getProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with SKU: " + sku)))
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
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product with ID " + productId + " not found.")))
                .flatMap(product -> {
                    String sku = product.getSku();
                    return productRepository.deleteById(productId)
                            .then(Mono.fromRunnable(() -> {
                                ProductMessageRequest message = new ProductMessageRequest(
                                        sku,
                                        "DELETE",
                                        null
                                );
                                rabbitMQJsonProducer.sendInventoryMessage(message);
                                log.info("DELETE event sent for product with SKU: {}", sku);
                            }))
                            .onErrorResume(e -> {
                                log.error("Error deleting product with ID {}: {}", productId, e.getMessage());
                                return Mono.error(e);
                            });
                }).then();
    }
}
