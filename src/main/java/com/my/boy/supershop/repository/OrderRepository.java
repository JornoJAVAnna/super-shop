package com.my.boy.supershop.repository;

import com.my.boy.supershop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Найти все заказы конкретного пользователя
    List<Order> findByUserId(Long userId);
}