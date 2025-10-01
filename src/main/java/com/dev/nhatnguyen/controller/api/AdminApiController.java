package com.dev.nhatnguyen.controller.api;

import com.dev.nhatnguyen.dto.MessageResponse;
import com.dev.nhatnguyen.entity.User;
import com.dev.nhatnguyen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {
    
    private final UserService userService;
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminDashboard() {
        List<User> users = userService.getAllUsers();
        
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("message", "Welcome to Admin Dashboard");
        dashboard.put("totalUsers", users.size());
        dashboard.put("activeUsers", users.stream().filter(User::isEnabled).count());
        dashboard.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        
        // Convert to DTO to avoid sending password
        List<Map<String, Object>> userDtos = users.stream().map(user -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", user.getId());
            dto.put("username", user.getUsername());
            dto.put("fullName", user.getFullName());
            dto.put("email", user.getEmail());
            dto.put("enabled", user.isEnabled());
            dto.put("roles", user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(userDtos);
    }
    
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            
            Map<String, Object> userDto = new HashMap<>();
            userDto.put("id", user.getId());
            userDto.put("username", user.getUsername());
            userDto.put("fullName", user.getFullName());
            userDto.put("email", user.getEmail());
            userDto.put("enabled", user.isEnabled());
            userDto.put("roles", user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList()));
            
            return ResponseEntity.ok(userDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse("Cannot delete user: " + e.getMessage(), false));
        }
    }
    
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStatistics() {
        List<User> users = userService.getAllUsers();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", users.size());
        stats.put("activeUsers", users.stream().filter(User::isEnabled).count());
        stats.put("inactiveUsers", users.stream().filter(u -> !u.isEnabled()).count());
        stats.put("adminUsers", users.stream()
            .filter(u -> u.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN")))
            .count());
        stats.put("regularUsers", users.stream()
            .filter(u -> u.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ROLE_USER")) &&
                u.getRoles().stream()
                .noneMatch(r -> r.getName().equals("ROLE_ADMIN")))
            .count());
        
        return ResponseEntity.ok(stats);
    }
}
