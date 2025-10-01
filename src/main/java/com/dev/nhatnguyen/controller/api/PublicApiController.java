package com.dev.nhatnguyen.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicApiController {
    
    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from Spring Security 6 API!");
        response.put("timestamp", LocalDateTime.now());
        response.put("authenticated", false);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/info")
    public ResponseEntity<?> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "Spring Security 6 Demo with JWT");
        info.put("version", "1.0.0");
        info.put("features", new String[]{
            "Form-based Authentication",
            "JWT Token Authentication",
            "Role-based Authorization",
            "Spring Data JPA",
            "H2 Database",
            "Thymeleaf + Bootstrap UI"
        });
        
        return ResponseEntity.ok(info);
    }
}
