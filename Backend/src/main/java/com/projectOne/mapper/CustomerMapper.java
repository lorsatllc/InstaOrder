package com.projectOne.mapper;

import com.projectOne.dto.response.customerResponse.CustomerInternalResponse;
import com.projectOne.dto.response.customerResponse.CustomerResponse;
import com.projectOne.entity.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static CustomerResponse toResponse(Customer customer) {


        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();

    }

    public static List<CustomerResponse> toResponseList(List<Customer> customers){

        return customers.stream()
                .map(CustomerMapper::toResponse)
                .collect(Collectors.toList());

    }

    public static CustomerInternalResponse toResponseInternal(Customer customer){

        return CustomerInternalResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .build();

    }
}
