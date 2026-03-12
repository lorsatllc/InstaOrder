package com.projectOne.controller;

import com.projectOne.dto.request.orderRequest.CreateOrderRequest;
import com.projectOne.dto.request.orderRequest.UpdateOrderStatus;
import com.projectOne.dto.response.orderResponse.AllOrdersResponse;
import com.projectOne.entity.Order;
import com.projectOne.service.OrderService;
import jakarta.validation.Valid;
import com.projectOne.dto.response.orderResponse.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/orders/create")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {

        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable long id) {
        return orderService.getOrderById(id);

    }

    @GetMapping("/orders")
    public ResponseEntity<Iterable<AllOrdersResponse>> getAllOrders() {
        Iterable<AllOrdersResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/orders/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order changedOrder) {
        Order updatedOrder = orderService.updateOrder(id, changedOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/orders/updatestatus/{id}")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id, @RequestBody UpdateOrderStatus newStatus) {

        OrderResponse updateOrder = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(updateOrder);

    }

    @DeleteMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order Deleted Successfully";

    }

}
