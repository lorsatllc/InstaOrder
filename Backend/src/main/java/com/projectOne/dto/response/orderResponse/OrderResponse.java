package com.projectOne.dto.response.orderResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projectOne.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@JsonPropertyOrder({
        "orderId",
        "customerId",
        "orderDate",
        "orderTime",
        "status",
        "items",
        "totalAmount"
})
@Data
@Builder
public class OrderResponse {

    private Long orderId;
    private Long customerId;

    private LocalDate orderDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime orderTime;

    private OrderStatus status;

    private List<OrderItemResponse> items;

    private Double totalAmount;

}
