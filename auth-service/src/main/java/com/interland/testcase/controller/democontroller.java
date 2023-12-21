package com.interland.testcase.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class democontroller {

    @GetMapping
    @PreAuthorize("hasRole('client_user')")
    public String hello() {
        return "Hello from Spring boot & Keycloak";
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('client_admin')")
    public String hello2() {
        return "Hello from Spring boot & Keycloak - ADMIN";
    }
}
