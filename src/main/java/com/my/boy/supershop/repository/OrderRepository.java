package com.my.boy.supershop.repository;

import com.my.boy.supershop.entity.Order;
import com.my.boy.supershop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}