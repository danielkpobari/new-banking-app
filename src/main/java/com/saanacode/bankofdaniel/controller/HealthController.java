package com.saanacode.bankofdaniel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "App is running...🔥🔥🔥";
    }
}
