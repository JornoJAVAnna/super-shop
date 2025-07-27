package com.my.boy.supershop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "orders") // "order" — зарезервированное слово
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
    private Long id;

    // Владелец заказа
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Время заказа
    private LocalDateTime createdAt = LocalDateTime.now();

    // Состав заказа
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void setItems(List<OrderItem> items) {
        this.items = items;
        // назначаем обратную связь
        for (OrderItem item : items) {
            item.setOrder(this);
        }
    }
}