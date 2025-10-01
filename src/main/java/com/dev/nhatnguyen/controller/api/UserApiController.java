package com.dev.nhatnguyen.controller.api;

import com.dev.nhatnguyen.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {
    
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        Map<String, Object> profile = new HashMap<>();
        profile.put("username", authentication.getName());
        profile.put("authorities", authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        profile.put("authenticated", authentication.isAuthenticated());
        
        return ResponseEntity.ok(profile);
    }
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getUserDashboard(Authentication authentication) {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("message", "Welcome to User Dashboard");
        dashboard.put("username", authentication.getName());
        dashboard.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(dashboard);
    }
    
    @PostMapping("/update-profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> updates, 
                                          Authentication authentication) {
        // This is a demo endpoint - implement actual profile update logic
        return ResponseEntity.ok(new MessageResponse(
            "Profile updated successfully for user: " + authentication.getName()
        ));
    }
}
