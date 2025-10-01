# 📊 Tổng Quan Dự Án - Spring Security 6 Demo

## 🎯 Mục đích dự án

Dự án demo **hoàn chỉnh** về Spring Security 6 với đầy đủ tính năng:
- ✅ **Web Authentication** (Form-based) với Thymeleaf + Bootstrap
- ✅ **REST API Authentication** (JWT Token-based)
- ✅ **Role-based Authorization**
- ✅ **H2 Database** với Spring Data JPA
- ✅ **Responsive UI** với Bootstrap 5

## 📦 Công nghệ sử dụng

| Công nghệ | Version | Mục đích |
|-----------|---------|----------|
| Spring Boot | 3.5.6 | Framework chính |
| Spring Security | 6.x | Bảo mật |
| Spring Data JPA | Latest | ORM, Database access |
| H2 Database | Latest | In-memory database |
| Thymeleaf | Latest | Template engine |
| Bootstrap | 5.3.0 | UI Framework |
| JWT (jjwt) | 0.12.3 | JSON Web Token |
| Lombok | Latest | Reduce boilerplate code |

## 🗂️ Cấu trúc code

```
📁 src/main/java/com/dev/nhatnguyen/
├── 📁 config/                     Cấu hình Spring Security & Data Initializer
├── 📁 controller/                 Web Controllers (MVC)
│   └── 📁 api/                    REST API Controllers
├── 📁 dto/                        Data Transfer Objects
├── 📁 entity/                     JPA Entities (User, Role)
├── 📁 repository/                 Spring Data Repositories
├── 📁 security/                   Security classes (JWT, UserDetails)
└── 📁 service/                    Business Logic Services

📁 src/main/resources/
├── 📁 templates/                  Thymeleaf HTML templates
│   ├── 📁 fragments/              Reusable fragments
│   ├── 📁 admin/                  Admin pages
│   └── 📁 user/                   User pages
└── 📄 application.properties      Configuration
```

## 🌟 Tính năng chính

### 1. Xác thực 2 cách (Dual Authentication)

#### A. Form-based (Web)
- Đăng nhập qua form HTML
- Session-based authentication
- Cookie management
- Remember me functionality

#### B. JWT Token (API)
- Stateless authentication
- Bearer token trong header
- Token expiration: 24 giờ
- RESTful API endpoints

### 2. Phân quyền (Authorization)

#### Roles:
- **ROLE_ADMIN**: Toàn quyền
- **ROLE_USER**: Quyền cơ bản

#### Protected Resources:
- Web: `/admin/**`, `/user/**`
- API: `/api/admin/**`, `/api/user/**`

### 3. Bảo mật

| Tính năng | Mô tả |
|-----------|-------|
| Password Encryption | BCrypt với cost factor 10 |
| CSRF Protection | Bật cho web, tắt cho API |
| XSS Protection | Headers configuration |
| JWT Security | HMAC-SHA256 signing |
| SQL Injection | JPA Prepared Statements |

## 📝 Files quan trọng

| File | Mô tả |
|------|-------|
| `README.md` | Hướng dẫn đầy đủ về dự án |
| `API_TEST.md` | Chi tiết test các API endpoints |
| `JWT_QUICK_START.md` | Quick start guide cho JWT |
| `PROJECT_SUMMARY.md` | File này - Tổng quan dự án |

## 🚀 Chạy dự án

### Bước 1: Clone/Extract dự án
```bash
cd BT_WEB_09
```

### Bước 2: Chạy ứng dụng
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### Bước 3: Truy cập
- **Web UI**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
- **API**: http://localhost:8080/api/...

## 👥 Tài khoản demo

| Type | Username | Password | Role | Use Case |
|------|----------|----------|------|----------|
| Admin | `admin` | `admin123` | ROLE_ADMIN | Quản trị hệ thống |
| User | `user` | `user123` | ROLE_USER | Người dùng thông thường |

## 🧪 Test Scenarios

### Scenario 1: Test Web Authentication
1. Truy cập http://localhost:8080
2. Click "Đăng nhập"
3. Login với `admin/admin123`
4. Xem Admin Dashboard
5. Quản lý users

### Scenario 2: Test JWT API
1. POST login để lấy token:
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"admin123"}'
   ```
2. Copy token từ response
3. GET protected endpoint:
   ```bash
   curl -X GET http://localhost:8080/api/admin/users \
     -H "Authorization: Bearer {token}"
   ```

### Scenario 3: Test Authorization
1. Login với `user/user123`
2. Try truy cập `/admin/dashboard` → Bị chặn 403
3. Truy cập `/user/dashboard` → OK
4. Test API với user token → Chỉ access được `/api/user/**`

## 📊 API Endpoints Summary

### Public APIs (Không cần auth)
```
GET  /api/public/hello
GET  /api/public/info
POST /api/auth/login
POST /api/auth/register
```

### User APIs (ROLE_USER)
```
GET  /api/user/profile
GET  /api/user/dashboard
POST /api/user/update-profile
```

### Admin APIs (ROLE_ADMIN)
```
GET    /api/admin/dashboard
GET    /api/admin/users
GET    /api/admin/users/{id}
DELETE /api/admin/users/{id}
GET    /api/admin/stats
```

## 🔐 Security Flow

### Web Flow:
```
User → Login Form → Spring Security Filter
     → UserDetailsService → Database
     → Authentication Success → Create Session
     → Redirect to Home
```

### API Flow:
```
Client → POST /api/auth/login → AuthenticationManager
       → Validate credentials → Generate JWT Token
       → Return token to client

Client → API Request + Bearer Token → JwtAuthenticationFilter
       → Validate Token → Extract User → SecurityContext
       → Controller → Response
```

## 📚 Database Schema

### Table: USERS
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key |
| username | VARCHAR(50) | Unique, Not Null |
| password | VARCHAR(255) | BCrypt encrypted |
| full_name | VARCHAR(100) | Full name |
| email | VARCHAR(100) | Unique, Not Null |
| enabled | BOOLEAN | Active status |

### Table: ROLES
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key |
| name | VARCHAR(50) | Role name (ROLE_*) |
| description | VARCHAR(200) | Description |

### Table: USER_ROLES (Join Table)
| Column | Type | Description |
|--------|------|-------------|
| user_id | BIGINT | FK to users |
| role_id | BIGINT | FK to roles |

## 🎓 Learning Points

### Spring Security 6 Changes
- ❌ ~~WebSecurityConfigurerAdapter~~ (Deprecated)
- ✅ SecurityFilterChain Bean approach
- ✅ Lambda DSL configuration
- ✅ Modern annotation-based security

### JWT Best Practices
- ✅ Separate secret key
- ✅ Token expiration
- ✅ Stateless design
- ✅ Bearer token standard
- ✅ Proper error handling

### Architecture Patterns
- ✅ MVC pattern cho web
- ✅ REST API design
- ✅ DTO pattern
- ✅ Service layer
- ✅ Repository pattern

## 🐛 Troubleshooting

### Problem: 401 Unauthorized on API
**Solution**: Kiểm tra token có đúng và chưa expired

### Problem: 403 Forbidden
**Solution**: Kiểm tra user có đúng role không

### Problem: Cannot login
**Solution**: Kiểm tra username/password, xem logs

### Problem: H2 Console không hiện
**Solution**: Đảm bảo đã config đúng JDBC URL: `jdbc:h2:mem:securitydb`

## 📈 Mở rộng dự án

### Ý tưởng mở rộng:
1. **Refresh Token**: Implement refresh token mechanism
2. **Email Verification**: Xác thực email khi đăng ký
3. **Password Reset**: Quên mật khẩu
4. **OAuth2**: Đăng nhập bằng Google/Facebook
5. **Audit Log**: Log tất cả actions
6. **Rate Limiting**: Giới hạn số request
7. **Redis**: Cache sessions và tokens
8. **MySQL/PostgreSQL**: Production database
9. **Docker**: Containerization
10. **Swagger/OpenAPI**: API documentation

## 📞 Support

Nếu gặp vấn đề:
1. Đọc README.md
2. Đọc API_TEST.md
3. Check logs trong console
4. Debug với breakpoints
5. Xem H2 console để check data

## 🎯 Next Steps

1. ✅ Chạy và test dự án
2. ✅ Đọc code để hiểu flow
3. ✅ Test tất cả endpoints
4. ✅ Customize theo nhu cầu
5. ✅ Deploy lên server

---

## 📊 Project Statistics

- **Total Files**: 30+ Java files
- **Total Templates**: 12+ HTML files
- **API Endpoints**: 15+ endpoints
- **Lines of Code**: ~2000+ lines
- **Time to setup**: 5 phút
- **Time to understand**: 2-3 giờ

---

**Dự án được tạo để học tập và demo. Hoàn toàn có thể customize và mở rộng! 🚀**

*Last updated: 2025-10-01*
