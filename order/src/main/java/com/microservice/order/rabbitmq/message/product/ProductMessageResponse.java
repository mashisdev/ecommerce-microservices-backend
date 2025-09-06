package com.microservice.order.rabbitmq.message.product;

import java.util.List;

public record ProductMessageResponse(
    String orderTrackingNumber,
    List<ProductItem> productItems
) {}
