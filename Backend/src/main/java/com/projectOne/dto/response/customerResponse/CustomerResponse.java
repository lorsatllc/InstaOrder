package com.projectOne.dto.response.customerResponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projectOne.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "role"
})
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;
}
