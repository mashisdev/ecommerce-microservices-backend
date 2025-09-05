package com.microservice.order.controller;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.dto.request.OrderRequest;
import com.microservice.order.dto.response.CreateOrderResponse;
import com.microservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Mono<ResponseEntity<CreateOrderResponse>> placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request)
                .map(orderTrackingNumber ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(new CreateOrderResponse(orderTrackingNumber))
                );
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<OrderDto>> getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId).map(ResponseEntity::ok);
    }

    @GetMapping("/tracking/{orderTrackingNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<OrderDto>> getOrderByOrderTrackingNumber(@PathVariable String orderTrackingNumber) {
        return orderService.getOrderByOrderTrackingNumber(orderTrackingNumber).map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<Page<OrderDto>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return orderService.getAllOrders(pageable)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{orderId}/status")
    public Mono<ResponseEntity<OrderDto>> updateOrderStatus(@PathVariable UUID orderId, @RequestParam String newStatus) {
        return orderService.updateOrderStatus(orderId, newStatus)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{orderId}")
    public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable UUID orderId) {
        return orderService.deleteOrder(orderId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
