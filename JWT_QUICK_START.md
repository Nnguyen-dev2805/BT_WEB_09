# JWT Quick Start Guide

Hướng dẫn nhanh sử dụng JWT Authentication trong dự án.

## 🚀 Bắt đầu nhanh

### 1. Khởi động ứng dụng

```bash
mvnw.cmd spring-boot:run
```

### 2. Test JWT ngay với cURL

**Windows PowerShell:**
```powershell
# Login và lấy token
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"username":"admin","password":"admin123"}'

$token = $response.token
Write-Host "Token: $token"

# Sử dụng token để gọi API
Invoke-RestMethod -Uri "http://localhost:8080/api/admin/users" `
  -Method GET `
  -Headers @{"Authorization"="Bearer $token"}
```

**Linux/Mac:**
```bash
# Login và lưu token
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | jq -r '.token')

echo "Token: $TOKEN"

# Sử dụng token
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer $TOKEN"
```

## 📝 Các endpoint quan trọng

### 1. Login (Lấy JWT Token)
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "admin",
  "roles": ["ROLE_ADMIN"]
}
```

### 2. Sử dụng Token
Thêm token vào header:
```
Authorization: Bearer {your-token-here}
```

### 3. Test với Protected Endpoint
```http
GET /api/admin/users
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## 🔑 Tài khoản test

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| user | user123 | USER |

## 📚 Tài liệu chi tiết

Xem file [API_TEST.md](API_TEST.md) để biết thêm chi tiết về:
- Tất cả API endpoints
- Examples với Postman
- Examples với JavaScript/Axios
- Troubleshooting

## ⚙️ Cấu hình JWT

Trong `application.properties`:
```properties
# JWT Secret Key
jwt.secret=MySecretKeyForJWTTokenGenerationAndValidation12345678901234567890

# JWT Expiration Time (24 hours)
jwt.expiration=86400000
```

## 🛠️ Cấu trúc JWT

Dự án sử dụng các class sau:

- **JwtUtil**: Generate và validate JWT token
- **JwtAuthenticationFilter**: Filter để xác thực JWT
- **JwtRequest**: DTO cho login request
- **JwtResponse**: DTO cho login response

## 🌐 API Categories

1. **Public APIs** (`/api/public/**`): Không cần authentication
2. **Auth APIs** (`/api/auth/**`): Login, Register
3. **User APIs** (`/api/user/**`): Yêu cầu ROLE_USER hoặc ROLE_ADMIN
4. **Admin APIs** (`/api/admin/**`): Chỉ ROLE_ADMIN

## ✅ Checklist

- [ ] Khởi động ứng dụng
- [ ] Test public endpoint: `/api/public/hello`
- [ ] Login để lấy token
- [ ] Test protected endpoint với token
- [ ] Test với tài khoản USER và ADMIN
- [ ] Xem danh sách users qua API

## 🎯 Next Steps

1. Đọc [API_TEST.md](API_TEST.md) để test tất cả endpoints
2. Test với Postman hoặc REST Client
3. Tích hợp với frontend application
4. Customize JWT expiration time nếu cần

---

**Happy Coding! 🚀**
