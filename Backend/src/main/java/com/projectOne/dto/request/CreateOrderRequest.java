package com.projectOne.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull
    private Long customerId;

    @NotEmpty
    private List<OrderedItemRequest> items;

}
