package com.my.boy.supershop.service.impl;


import com.my.boy.supershop.dto.OrderDto;
import com.my.boy.supershop.dto.OrderItemDto;
import com.my.boy.supershop.entity.Order;
import com.my.boy.supershop.entity.OrderItem;
import com.my.boy.supershop.entity.Product;
import com.my.boy.supershop.entity.User;
import com.my.boy.supershop.repository.OrderRepository;
import com.my.boy.supershop.repository.ProductRepository;
import com.my.boy.supershop.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public OrderDto createOrder(List<OrderItemDto> itemsDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> items = itemsDto.stream().map(dto -> {
            Product product = productRepository.findById(dto.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + dto.productId()));
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(dto.quantity());
            item.setOrder(order);
            return item;
        }).toList();

        order.setItems(items);
        Order saved = orderRepository.save(order);

        return mapToDto(saved);
    }

    public List<OrderDto> getMyOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return orderRepository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private OrderDto mapToDto(Order order) {
        List<OrderItemDto> items = order.getItems().stream()
                .map(item -> new OrderItemDto(item.getProduct().getId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new OrderDto(order.getId(), items, order.getCreatedAt());
    }
}