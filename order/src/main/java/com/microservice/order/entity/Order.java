package com.microservice.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name="orders")
@Getter
@Setter
public class Order {

    @Id
    private UUID id;

    @Column("order_tracking_number")
    private String orderTrackingNumber;

    @Column("total_quantity")
    private int totalQuantity;

    @Column("total_price")
    private BigDecimal totalPrice;

    private OrderStatus status;

    // Auditing
    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
//    private Set<OrderItem> orderItems = new HashSet<>();

    @Transient
    private Set<OrderItem> orderItems = new HashSet<>();

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;

//    @Column("user_id")
//    private Long userId;
}