package com.projectOne.dto.response.orderResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projectOne.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@JsonPropertyOrder({
        "orderId",
        "customerId",
        "customerName",
        "orderDate",
        "orderTime",
        "status",
        "items",
        "totalAmount"

})
public class AllOrdersResponse {


    private Long orderId;
    private Long customerId;
    private String customerName;
    private LocalDate orderDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime orderTime;

    private OrderStatus status;
    private List<OrderItemResponse> items;
    private Double totalAmount;
}
