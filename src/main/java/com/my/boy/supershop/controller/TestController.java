package com.my.boy.supershop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "Доступ разрешён: USER";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Доступ разрешён: ADMIN";
    }

    @GetMapping("/all")
    public String publicAccess() {
        return "Публичный доступ — доступно всем";
    }
}