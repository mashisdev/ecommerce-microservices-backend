package com.microservice.product.repository;

import com.microservice.product.entity.Brand;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends R2dbcRepository<Brand, Long> {
}
