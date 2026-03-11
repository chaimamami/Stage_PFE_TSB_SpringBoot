package com.banque.cheques.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/cons-c")
public class ConsCDashboardController {

    @GetMapping("/dashboard")
    public Map<String, String> dashboard() {
        return Map.of(
                "message", "Bienvenue depuis le backend CONS-C"
        );
    }
}