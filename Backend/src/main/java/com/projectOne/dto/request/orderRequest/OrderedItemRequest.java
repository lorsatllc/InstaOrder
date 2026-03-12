package com.projectOne.dto.request.orderRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderedItemRequest {

    @NotNull
    private Integer itemId;

    @Min(1)
    private Integer quantity;
}
