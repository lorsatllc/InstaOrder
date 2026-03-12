package com.projectOne.dto.request.orderRequest;

import com.projectOne.entity.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusUpdate {

    private OrderStatus status;
}
