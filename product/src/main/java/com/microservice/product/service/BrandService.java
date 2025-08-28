package com.microservice.product.service;

import com.microservice.product.dto.BrandDto;
import com.microservice.product.dto.request.BrandRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BrandService {

    Mono<BrandDto> createBrand(BrandRequest request);
    Mono<BrandDto> getBrandById(Long id);
    Flux<BrandDto> getAllBrands();
    Mono<BrandDto> updateBrand(Long id, BrandRequest request);
    Mono<Void> deleteBrand(Long id);
}
