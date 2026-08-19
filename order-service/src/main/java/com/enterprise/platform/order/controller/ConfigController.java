package com.enterprise.platform.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${app.message}")
    private String message;

    @Value("${app.timeout}")
    private String timeout;

    @GetMapping("/message")
    public String message() {
        return message;
    }

    @GetMapping("/timeout")
    public String timeout() {
        return timeout;
    }
}