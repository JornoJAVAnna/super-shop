package com.my.boy.supershop.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(Long id, List<OrderItemDto> items, LocalDateTime createdAt) {
}
