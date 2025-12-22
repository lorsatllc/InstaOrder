package com.projectOne.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

    private Integer itemId;
    private  String itemName;

    private int quantity;
    private Double unitPrice;
    private Double totalPrice;
}
