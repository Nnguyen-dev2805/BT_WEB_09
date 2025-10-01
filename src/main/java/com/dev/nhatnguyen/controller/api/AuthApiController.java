package com.dev.nhatnguyen.controller.api;

import com.dev.nhatnguyen.dto.JwtRequest;
import com.dev.nhatnguyen.dto.JwtResponse;
import com.dev.nhatnguyen.dto.MessageResponse;
import com.dev.nhatnguyen.dto.UserRegistrationDto;
import com.dev.nhatnguyen.security.CustomUserDetails;
import com.dev.nhatnguyen.security.JwtUtil;
import com.dev.nhatnguyen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    jwtRequest.getUsername(),
                    jwtRequest.getPassword()
                )
            );
            
            // Get user details
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            
            // Generate JWT token
            String jwt = jwtUtil.generateToken(userDetails);
            
            // Extract roles
            List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Tên đăng nhập hoặc mật khẩu không đúng!", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Đã xảy ra lỗi: " + e.getMessage(), false));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto registrationDto) {
        try {
            // Validate password match
            if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
                return ResponseEntity.badRequest()
                    .body(new MessageResponse("Mật khẩu xác nhận không khớp!", false));
            }
            
            // Register user
            userService.registerUser(registrationDto);
            
            return ResponseEntity.ok(new MessageResponse("Đăng ký thành công!"));
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse(e.getMessage(), false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Đã xảy ra lỗi: " + e.getMessage(), false));
        }
    }
}
