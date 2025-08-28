package com.microservice.product.service.impl;

import com.microservice.product.dto.BrandDto;
import com.microservice.product.dto.request.BrandRequest;
import com.microservice.product.entity.Brand;
import com.microservice.product.mapper.BrandMapper;
import com.microservice.product.repository.BrandRepository;
import com.microservice.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Transactional
    @Override
    public Mono<BrandDto> createBrand(BrandRequest request) {
        Brand newBrand = new Brand();
        newBrand.setName(request.name());
        return brandRepository.save(newBrand)
                .map(brandMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<BrandDto> getAllBrands() {
        return brandRepository.findAll()
                .map(brandMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<BrandDto> getBrandById(Long id) {
        return brandRepository.findById(id)
                .map(brandMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<BrandDto> updateBrand(Long id, BrandRequest request) {
        return brandRepository.findById(id)
                .flatMap(brand -> {
                    brand.setName(request.name());
                    return brandRepository.save(brand);
                })
                .map(brandMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<Void> deleteBrand(Long id) {
        return brandRepository.deleteById(id);
    }
}
