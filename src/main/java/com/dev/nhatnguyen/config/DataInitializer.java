package com.dev.nhatnguyen.config;

import com.dev.nhatnguyen.entity.Role;
import com.dev.nhatnguyen.entity.User;
import com.dev.nhatnguyen.repository.RoleRepository;
import com.dev.nhatnguyen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Tạo roles nếu chưa tồn tại
        if (roleRepository.count() == 0) {
            Role adminRole = Role.builder()
                    .name("ROLE_ADMIN")
                    .description("Administrator role")
                    .build();
            
            Role userRole = Role.builder()
                    .name("ROLE_USER")
                    .description("Default user role")
                    .build();
            
            roleRepository.save(adminRole);
            roleRepository.save(userRole);
            log.info("Roles created successfully");
        }
        
        // Tạo admin user mặc định nếu chưa tồn tại
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Administrator")
                    .email("admin@example.com")
                    .enabled(true)
                    .roles(adminRoles)
                    .build();
            
            userRepository.save(admin);
            log.info("Admin user created - Username: admin, Password: admin123");
        }
        
        // Tạo user thường mặc định nếu chưa tồn tại
        if (!userRepository.existsByUsername("user")) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("User role not found"));
            
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .fullName("Regular User")
                    .email("user@example.com")
                    .enabled(true)
                    .roles(userRoles)
                    .build();
            
            userRepository.save(user);
            log.info("Regular user created - Username: user, Password: user123");
        }
    }
}
