package com.microservice.product.message.request;

import java.util.List;

public record OrderMessage(String orderTrackingNumber, List<OrderItemRequest> items) {}

