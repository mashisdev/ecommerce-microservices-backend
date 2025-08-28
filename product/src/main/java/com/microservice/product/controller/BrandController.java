package com.microservice.product.controller;

import com.microservice.product.dto.BrandDto;
import com.microservice.product.dto.request.BrandRequest;
import com.microservice.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public Flux<BrandDto> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BrandDto>> getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BrandDto> createBrand(@Valid @RequestBody BrandRequest request) {
        return brandService.createBrand(request);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BrandDto>> updateBrand(@PathVariable Long id, @Valid @RequestBody BrandRequest request) {
        return brandService.updateBrand(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBrand(@PathVariable Long id) {
        return brandService.deleteBrand(id);
    }
}
