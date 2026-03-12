package com.projectOne.service;

import com.projectOne.dto.request.orderRequest.CreateOrderRequest;
import com.projectOne.dto.request.orderRequest.OrderedItemRequest;
import com.projectOne.dto.request.orderRequest.UpdateOrderStatus;
import com.projectOne.dto.response.orderResponse.AllOrdersResponse;
import com.projectOne.dto.response.orderResponse.OrderResponse;
import com.projectOne.entity.Order;
import com.projectOne.entity.OrderItem;
import com.projectOne.entity.enums.OrderStatus;
import com.projectOne.mapper.OrderMapper;
import com.projectOne.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projectOne.entity.Customer;
import com.projectOne.repository.CustomerRepository;
import com.projectOne.ExceptionHandling.NotFoundException;
import com.projectOne.entity.Item;
import com.projectOne.repository.ItemRepository;
import com.projectOne.Logger.ApplicationLogger;

import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ApplicationLogger loggerService;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        loggerService.info("Fetching customer's details...");
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found!" + request.getCustomerId()));
        loggerService.info("Customer Details: " + customer);
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setOrderTime(LocalTime.now());
        order.setStatus(OrderStatus.ACCEPTED);

        for (OrderedItemRequest itemReq : request.getItems()){

            Item item = itemRepository.findById(itemReq.getItemId()).orElseThrow(() -> new NotFoundException("Item Not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setUnitPrice(BigDecimal.valueOf(item.getItemPrice()));

            order.addOrderItem(orderItem);

        }

        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toResponse(savedOrder);
    }

    public Order getOrderById(long id) {
        loggerService.info("Fetching order with ID: " + id);
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found with id " + id));
    }

    @Transactional
    public Order updateOrder(Long id, Order changedOrder) {

        loggerService.info("Fetching Order in DB... ");
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));

        existingOrder.setOrderDate(changedOrder.getOrderDate());
        existingOrder.setOrderTime(changedOrder.getOrderTime());
        existingOrder.setStatus(changedOrder.getStatus());

        loggerService.info("Existing order: " + existingOrder);
        existingOrder.getOrderItems().clear();

        if (changedOrder.getOrderItems() != null) {

            for (OrderItem oi : changedOrder.getOrderItems()) {

                if (oi.getItem() == null || oi.getItem().getItemId() == null) {
                    throw new IllegalArgumentException("Each order item must contain an item with an id");
                }

                Integer itemId = oi.getItem().getItemId();
                Item managedItem = itemRepository.findById(itemId).orElseThrow(
                        () -> new NotFoundException("Item not found with id " + itemId));

                OrderItem newOrderItem = new OrderItem();
                newOrderItem.setItem(managedItem);
                newOrderItem.setQuantity(oi.getQuantity());
                newOrderItem.setUnitPrice(oi.getUnitPrice());
                existingOrder.addOrderItem(newOrderItem);

            }
        }
        loggerService.info("Update order: " + existingOrder);
        orderRepository.save(existingOrder);
        return existingOrder;

    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, UpdateOrderStatus newStatus) {

        loggerService.info("Updating order status to:: " + newStatus.getStatus());
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));

        existingOrder.setStatus(OrderStatus.valueOf(newStatus.getStatus()));

        return OrderMapper.toResponse(existingOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {

        loggerService.info("Deleting order with ID: " + id);
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));

        if (existingOrder.getCustomer() != null) {
            existingOrder.getCustomer().getOrders().remove(existingOrder);
        }
        orderRepository.delete(existingOrder);
    }

    public Iterable<AllOrdersResponse> getAllOrders() {
        loggerService.info("Fetching all orders...");


        Iterable<Order> result =  orderRepository.findAll();

        return OrderMapper.toOrdersResponse(result);

    }

}
