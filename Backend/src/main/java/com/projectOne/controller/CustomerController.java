package com.projectOne.controller;

import com.projectOne.dto.request.customerRequest.CreateCustomerRequest;
import com.projectOne.dto.response.customerResponse.CustomerInternalResponse;
import com.projectOne.dto.response.customerResponse.CustomerResponse;
import com.projectOne.entity.Customer;
import com.projectOne.mapper.CustomerMapper;
import com.projectOne.service.CustomerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.projectOne.entity.Order;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        return ResponseEntity.ok(service.getAllCustomer());
    }

    @GetMapping("/customers/{id}")
    public CustomerResponse getSingleCustomer(@PathVariable long id) {

        return CustomerMapper.toResponse(service.getCustomerById(id));

    }

    @GetMapping("/internal/customers/{id}")
    public CustomerInternalResponse getSingleCustomerInternal(@PathVariable long id) {

        return CustomerMapper.toResponseInternal(service.getCustomerById(id));

    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customers/orders/{id}")
    public ResponseEntity<Iterable<Order>> getCustomerOrders(@PathVariable long id) {
        Iterable<Order> orders = service.getCustomerOrders(id);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/auth/addcustomers")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CreateCustomerRequest customer) {
        CustomerResponse savedCustomer = service.addCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PatchMapping("/customers/update/{id}")
    public String updateCustomerDetails(@RequestBody CreateCustomerRequest customer,@PathVariable Long id) {
        Long custId = id;
        Customer currentCustomer = service.getCustomerById(custId);
        currentCustomer.setName(customer.getName());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setPassword(customer.getPassword());
        service.updateCustomer(currentCustomer);
        return "Updated Successfully";
    }

    @DeleteMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable long id) {
        service.deleteCustomerById(id);
        return "Account Deleted";
    }

}