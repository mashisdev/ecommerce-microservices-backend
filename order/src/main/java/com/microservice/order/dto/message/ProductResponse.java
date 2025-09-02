package com.microservice.order.dto.message;

import java.util.List;

public record ProductResponse (
    String orderTrackingNumber,
    List<ProductItem> productItems
){}
