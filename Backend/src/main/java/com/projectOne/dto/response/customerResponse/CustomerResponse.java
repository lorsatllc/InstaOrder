package com.projectOne.dto.response.customerResponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({
        "id",
        "name",
        "email"
})
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
}
