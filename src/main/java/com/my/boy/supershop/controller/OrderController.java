package com.my.boy.supershop.controller;

import com.my.boy.supershop.dto.OrderDto;
import com.my.boy.supershop.dto.OrderItemDto;
import com.my.boy.supershop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Создать заказ — доступен авторизованным пользователям с ролью USER или ADMIN
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody List<OrderItemDto> items) {
        OrderDto createdOrder = orderService.createOrder(items);
        return ResponseEntity.ok(createdOrder);
    }

    // Получить свои заказы — для USER и ADMIN (ADMIN увидит только свои заказы)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> getMyOrders() {
        List<OrderDto> orders = orderService.getOrdersForCurrentUser();
        return ResponseEntity.ok(orders);
    }

    // Получить все заказы — только для ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
