package com.microservice.product.repository;

import com.microservice.product.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends R2dbcRepository<Product,Long> {

    Mono<Product> findBySku(String sku);
}