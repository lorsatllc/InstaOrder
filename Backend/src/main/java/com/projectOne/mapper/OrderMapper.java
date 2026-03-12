package com.projectOne.mapper;

import com.projectOne.dto.response.orderResponse.AllOrdersResponse;
import com.projectOne.dto.response.orderResponse.OrderItemResponse;
import com.projectOne.dto.response.orderResponse.OrderResponse;
import com.projectOne.entity.Order;
import com.projectOne.entity.OrderItem;

import java.util.List;
import java.util.stream.StreamSupport;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(OrderMapper::toItemResponse)
                .toList();


        double totalAmount = items.stream()
                .mapToDouble(OrderItemResponse::getTotalPrice)
                .sum();


        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer().getId())
                .orderDate(order.getOrderDate())
                .orderTime(order.getOrderTime())
                .status(order.getStatus())
                .items(items)
                .totalAmount(totalAmount)
                .build();


    }

    private static OrderItemResponse toItemResponse(OrderItem item) {

        return OrderItemResponse.builder()
                .itemId(item.getItem().getItemId())
                .itemName(item.getItem().getItemName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice().doubleValue())
                .totalPrice(
                        item.getUnitPrice()
                                .multiply(java.math.BigDecimal.valueOf(item.getQuantity()))
                                .doubleValue()
                )
                .build();
    }

    public static Iterable<AllOrdersResponse> toOrdersResponse(Iterable<Order> orders) {

        return StreamSupport.stream(orders.spliterator(),false)
                .map(OrderMapper::toAllOrdersResponse)
                .toList();


    }


    public static AllOrdersResponse toAllOrdersResponse(Order order) {

        double totalAmount = order.getOrderItems()
                .stream().mapToDouble(i -> i.getUnitPrice().doubleValue() * i.getQuantity())
                .sum();

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(OrderMapper::toItemResponse)
                .toList();


        return AllOrdersResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer().getId())
                .customerName(order.getCustomer().getName())
                .orderDate(order.getOrderDate())
                .orderTime(order.getOrderTime())
                .status(order.getStatus())
                .items(items)
                .totalAmount(totalAmount)
                .build();

    }
}

