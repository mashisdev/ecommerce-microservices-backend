package com.microservice.product.controller;

import com.microservice.product.entity.Product;
import com.microservice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    public final ProductRepository productService;

    @PostMapping
    public ResponseEntity<Mono<Product>> create(@RequestBody Product product){
        Mono<Product> saved = productService.save(product);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
