package com.microservice.product.message;

import java.util.List;

public record OrderMessage(String orderTrackingNumber, List<OrderItemRequest> items) {}

