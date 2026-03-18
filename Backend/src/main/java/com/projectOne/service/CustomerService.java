package com.projectOne.service;

import com.projectOne.dto.request.customerRequest.CreateCustomerRequest;
import com.projectOne.dto.response.customerResponse.CustomerResponse;
import com.projectOne.entity.Customer;
import com.projectOne.entity.enums.Role;
import com.projectOne.mapper.CustomerMapper;
import com.projectOne.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.projectOne.ExceptionHandling.NotFoundException;
import com.projectOne.Logger.ApplicationLogger;
import com.projectOne.entity.Order;
import com.projectOne.repository.OrderRepository;

//import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {


    private final CustomerRepository repository;


    private final OrderRepository orderRepository;


    private final ApplicationLogger loggerService;


    private final PasswordEncoder passwordEncoder;

    public CustomerResponse addCustomer(CreateCustomerRequest customer) {
        loggerService.info("Adding new customer");
        Customer newCustomer = new Customer();

        newCustomer.setName(customer.getName());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));
        if(customer.getRole()==null || customer.getRole().equals("CUSTOMER")){
            newCustomer.setRole(Role.CUSTOMER);
        }
        else if(customer.getRole().equals("ADMIN")){
            newCustomer.setRole(Role.ADMIN);
        }

        Customer savedCustomer = repository.save(newCustomer);

        return CustomerMapper.toResponse(savedCustomer);
    }

    public List<CustomerResponse> getAllCustomer() {

        loggerService.info("Fetching all customers");
        List<Customer> customers = repository.findAll();
        if (customers.isEmpty()) {
            throw new NotFoundException("Failed to fetch details..");
        }

        return CustomerMapper.toResponseList(customers);
    }

    public Customer getCustomerById(long id) {
        Customer result = repository.findById(id).orElse(null);
        loggerService.info("Fetching customer with ID: " + id);
        loggerService.debug("Customer details: " + result);
        if (result == null) {
            throw new NotFoundException("No Account Found with this Id");
        }
        return result;
    }

    public void updateCustomer(Customer customer) {
        loggerService.info("Updating Customer: " + customer);
        repository.save(customer);
    }

    public void deleteCustomerById(long id) {
        loggerService.info("Deleting Customer with ID: " + id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Customer Found with this Id"));

        repository.deleteById(id);
        loggerService.info("Customer with ID " + customer.getId() + " deleted successfully.");
    }

    public Iterable<Order> getCustomerOrders(long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Customer Found with this Id"));

        // List<Order> customerOrders = new ArrayList<>();
        // orders.forEach((order) -> {
        // if (order.getCustomer().getId() == customer.getId()) {
        // customerOrders.add(order);
        // }
        // });
        loggerService.info("Fetching customer's orders...");
        List<Order> customerOrders = orderRepository.findByCustomer_Id(customer.getId());
        if (customerOrders.isEmpty()) {
            throw new NotFoundException("No Orders Found for this Customer");
        }

        return customerOrders;
    }

}
