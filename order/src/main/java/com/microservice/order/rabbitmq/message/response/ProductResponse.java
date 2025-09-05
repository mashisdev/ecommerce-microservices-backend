package com.microservice.order.rabbitmq.message.response;

import java.util.List;

public record ProductResponse (
    String orderTrackingNumber,
    List<ProductItem> productItems
) {}
