package com.projectOne.service;

import com.projectOne.Logger.ApplicationLogger;
import com.projectOne.dto.request.customerRequest.CreateCustomerRequest;
import com.projectOne.dto.response.customerResponse.CustomerResponse;
import com.projectOne.entity.Customer;
import com.projectOne.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ApplicationLogger loggerService;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void addCustomerTest() {

        Customer customer = new Customer();
        customer.setEmail("leo@gmail.com");
        customer.setName("Leo");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CreateCustomerRequest customerReq = new CreateCustomerRequest();
        customerReq.setName(customer.getName());
        customerReq.setEmail(customer.getName());
        customerReq.setPassword("Lea@123");
        CustomerResponse result = customerService.addCustomer(customerReq);

        assertNotNull(result);
        assertEquals("Leo",result.getName());

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(loggerService).info(anyString());



    }

    @Test
    void getCustomerByIdTest(){

        Long customerId =1L;
        Customer customer = new Customer();
        customer.setId(1234L);
        customer.setEmail("joe@gmail.com");
        customer.setName("Joe");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("Joe",result.getName());

    }
}
