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
import com.my.boy.supershop.service.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderDto createOrder(List<OrderItemDto> itemsDto) {
        // Получаем текущего пользователя из контекста безопасности
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

        // Преобразуем DTO в сущности OrderItem
        List<OrderItem> items = itemsDto.stream().map(dto -> {
            Product product = productRepository.findById(dto.productId())
                    .orElseThrow(() -> new RuntimeException("Продукт не найден: " + dto.productId()));

            OrderItem item = new OrderItem();
            item.setOrder(order);       // Связь с заказом
            item.setProduct(product);   // Связь с продуктом
            item.setQuantity(dto.quantity());
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);

        Order saved = orderRepository.save(order);

        return toDto(saved);
    }

    @Override
    public List<OrderDto> getOrdersForCurrentUser() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        List<Order> orders = orderRepository.findByUserId(user.getId());

        return orders.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Вспомогательный метод: Order → OrderDto
    private OrderDto toDto(Order order) {
        List<OrderItemDto> itemsDto = order.getItems().stream()
                .map(item -> new OrderItemDto(item.getProduct().getId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new OrderDto(order.getId(), itemsDto, order.getCreatedAt());
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            throw new RuntimeException("Пользователь не аутентифицирован");
        }
    }
}