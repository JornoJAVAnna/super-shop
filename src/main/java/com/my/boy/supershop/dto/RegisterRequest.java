package com.my.boy.supershop.dto;

public record RegisterRequest(
        String username,
        String email,
        String password
        ) {}
