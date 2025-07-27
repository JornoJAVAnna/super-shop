package com.my.boy.supershop.service;

import com.my.boy.supershop.dto.OrderDto;
import com.my.boy.supershop.dto.OrderItemDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(List<OrderItemDto> items);
    List<OrderDto> getOrdersForCurrentUser();
    List<OrderDto> getAllOrders();
}