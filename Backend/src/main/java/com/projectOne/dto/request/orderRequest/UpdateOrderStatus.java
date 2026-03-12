package com.projectOne.dto.request.orderRequest;

import com.projectOne.entity.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatus {

    @NotNull(message = "Order status is required")
    private String status;
}
