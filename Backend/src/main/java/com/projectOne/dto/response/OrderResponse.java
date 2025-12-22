package com.projectOne.dto.response;

import com.projectOne.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long orderId;
    private Long customerId;

    private LocalDate orderDate;
    private LocalTime orderTime;

    private OrderStatus status;

    private List<OrderItemResponse> items;

    private Double totalAmount;

}
