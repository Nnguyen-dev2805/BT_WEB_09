# Hướng dẫn Test JWT API

Tài liệu này hướng dẫn cách test các JWT API endpoints của dự án Spring Security 6 Demo.

## 📋 Mục lục

- [Công cụ cần thiết](#công-cụ-cần-thiết)
- [Public API Endpoints](#1-public-api-endpoints)
- [Authentication API](#2-authentication-api)
- [User API (Protected)](#3-user-api-protected)
- [Admin API (Protected)](#4-admin-api-protected)
- [Test với cURL](#test-với-curl)
- [Test với Postman](#test-với-postman)
- [Test với JavaScript](#test-với-javascript)

---

## Công cụ cần thiết

- **cURL**: Command line tool (có sẵn trên hầu hết các hệ điều hành)
- **Postman**: GUI tool để test API ([Download](https://www.postman.com/downloads/))
- **Thunder Client**: VS Code extension
- **REST Client**: VS Code extension

---

## 1. Public API Endpoints

### 1.1. Hello Endpoint

**Mô tả**: Endpoint công khai, không cần authentication

**Request:**
```http
GET http://localhost:8080/api/public/hello
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/public/hello
```

**Response:**
```json
{
  "message": "Hello from Spring Security 6 API!",
  "timestamp": "2025-10-01T13:30:00",
  "authenticated": false
}
```

### 1.2. Info Endpoint

**Request:**
```http
GET http://localhost:8080/api/public/info
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/public/info
```

**Response:**
```json
{
  "application": "Spring Security 6 Demo with JWT",
  "version": "1.0.0",
  "features": [
    "Form-based Authentication",
    "JWT Token Authentication",
    "Role-based Authorization",
    "Spring Data JPA",
    "H2 Database",
    "Thymeleaf + Bootstrap UI"
  ]
}
```

---

## 2. Authentication API

### 2.1. Login (Get JWT Token)

**Mô tả**: Đăng nhập để nhận JWT token

**Request:**
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

**Response (Success):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY...",
  "type": "Bearer",
  "username": "admin",
  "roles": ["ROLE_ADMIN"]
}
```

**Response (Error):**
```json
{
  "message": "Tên đăng nhập hoặc mật khẩu không đúng!",
  "success": false
}
```

### 2.2. Register New User

**Request:**
```http
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "confirmPassword": "password123",
  "fullName": "New User",
  "email": "newuser@example.com"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username":"newuser",
    "password":"password123",
    "confirmPassword":"password123",
    "fullName":"New User",
    "email":"newuser@example.com"
  }'
```

**Response:**
```json
{
  "message": "Đăng ký thành công!",
  "success": true
}
```

---

## 3. User API (Protected)

**Lưu ý**: Tất cả endpoints dưới đây yêu cầu JWT token trong header `Authorization: Bearer {token}`

### 3.1. Get User Profile

**Request:**
```http
GET http://localhost:8080/api/user/profile
Authorization: Bearer {your-jwt-token}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/user/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

**Response:**
```json
{
  "username": "user",
  "authorities": ["ROLE_USER"],
  "authenticated": true
}
```

### 3.2. Get User Dashboard

**Request:**
```http
GET http://localhost:8080/api/user/dashboard
Authorization: Bearer {your-jwt-token}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/user/dashboard \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

**Response:**
```json
{
  "message": "Welcome to User Dashboard",
  "username": "user",
  "timestamp": 1696145400000
}
```

### 3.3. Update Profile

**Request:**
```http
POST http://localhost:8080/api/user/update-profile
Authorization: Bearer {your-jwt-token}
Content-Type: application/json

{
  "fullName": "Updated Name",
  "email": "newemail@example.com"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/user/update-profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Updated Name","email":"newemail@example.com"}'
```

---

## 4. Admin API (Protected)

**Lưu ý**: Chỉ user có role `ROLE_ADMIN` mới có quyền truy cập

### 4.1. Get Admin Dashboard

**Request:**
```http
GET http://localhost:8080/api/admin/dashboard
Authorization: Bearer {admin-jwt-token}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

**Response:**
```json
{
  "message": "Welcome to Admin Dashboard",
  "totalUsers": 2,
  "activeUsers": 2,
  "timestamp": 1696145400000
}
```

### 4.2. Get All Users

**Request:**
```http
GET http://localhost:8080/api/admin/users
Authorization: Bearer {admin-jwt-token}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

**Response:**
```json
[
  {
    "id": 1,
    "username": "admin",
    "fullName": "Administrator",
    "email": "admin@example.com",
    "enabled": true,
    "roles": ["ROLE_ADMIN"]
  },
  {
    "id": 2,
    "username": "user",
    "fullName": "Regular User",
    "email": "user@example.com",
    "enabled": true,
    "roles": ["ROLE_USER"]
  }
]
```

### 4.3. Get User by ID

**Request:**
```http
GET http://localhost:8080/api/admin/users/1
Authorization: Bearer {admin-jwt-token}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/admin/users/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

### 4.4. Delete User

**Request:**
```http
DELETE http://localhost:8080/api/admin/users/3
Authorization: Bearer {admin-jwt-token}
```

**cURL:**
```bash
curl -X DELETE http://localhost:8080/api/admin/users/3 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

**Response:**
```json
{
  "message": "User deleted successfully",
  "success": true
}
```

### 4.5. Get Statistics

**Request:**
```http
GET http://localhost:8080/api/admin/stats
Authorization: Bearer {admin-jwt-token}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/admin/stats \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

**Response:**
```json
{
  "totalUsers": 3,
  "activeUsers": 3,
  "inactiveUsers": 0,
  "adminUsers": 1,
  "regularUsers": 2
}
```

---

## Test với cURL

### Quy trình test hoàn chỉnh:

**Bước 1: Login và lưu token**
```bash
# Windows PowerShell
$response = curl -X POST http://localhost:8080/api/auth/login `
  -H "Content-Type: application/json" `
  -d '{\"username\":\"admin\",\"password\":\"admin123\"}' | ConvertFrom-Json

$token = $response.token

# Linux/Mac
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | jq -r '.token')
```

**Bước 2: Sử dụng token để gọi protected endpoints**
```bash
# Windows PowerShell
curl -X GET http://localhost:8080/api/admin/users `
  -H "Authorization: Bearer $token"

# Linux/Mac
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer $TOKEN"
```

---

## Test với Postman

### Cấu hình Postman:

1. **Tạo Collection mới**: "Spring Security API Tests"

2. **Tạo Environment** với biến:
   - `baseUrl`: `http://localhost:8080`
   - `token`: (để trống, sẽ được set tự động)

3. **Login Request**:
   - Method: POST
   - URL: `{{baseUrl}}/api/auth/login`
   - Body (JSON):
     ```json
     {
       "username": "admin",
       "password": "admin123"
     }
     ```
   - Tests (để tự động lưu token):
     ```javascript
     var jsonData = pm.response.json();
     pm.environment.set("token", jsonData.token);
     ```

4. **Protected Requests**:
   - Tab Authorization → Type: Bearer Token
   - Token: `{{token}}`

---

## Test với JavaScript

### Sử dụng Fetch API:

```javascript
// 1. Login và lấy token
async function login() {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      username: 'admin',
      password: 'admin123'
    })
  });
  
  const data = await response.json();
  return data.token;
}

// 2. Gọi protected endpoint
async function getUsers() {
  const token = await login();
  
  const response = await fetch('http://localhost:8080/api/admin/users', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  
  const users = await response.json();
  console.log(users);
}

// Thực thi
getUsers();
```

### Sử dụng Axios:

```javascript
const axios = require('axios');

// 1. Login
async function login() {
  const response = await axios.post('http://localhost:8080/api/auth/login', {
    username: 'admin',
    password: 'admin123'
  });
  
  return response.data.token;
}

// 2. Get users với token
async function getUsers() {
  const token = await login();
  
  const response = await axios.get('http://localhost:8080/api/admin/users', {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  
  console.log(response.data);
}

getUsers();
```

---

## Lỗi thường gặp

### 1. 401 Unauthorized

**Nguyên nhân**:
- Token không hợp lệ hoặc đã hết hạn
- Thiếu header Authorization
- Username/password không đúng

**Giải pháp**:
- Login lại để lấy token mới
- Kiểm tra format header: `Authorization: Bearer {token}`

### 2. 403 Forbidden

**Nguyên nhân**:
- User không có quyền truy cập endpoint
- VD: User role USER cố truy cập `/api/admin/**`

**Giải pháp**:
- Đảm bảo user có đúng role
- Login với tài khoản admin để test admin endpoints

### 3. 400 Bad Request

**Nguyên nhân**:
- Dữ liệu request không hợp lệ
- Thiếu required fields

**Giải pháp**:
- Kiểm tra format JSON
- Đảm bảo gửi đủ các trường bắt buộc

---

## Tài khoản test

| Username | Password | Role | Mô tả |
|----------|----------|------|-------|
| `admin` | `admin123` | ROLE_ADMIN | Tài khoản quản trị viên |
| `user` | `user123` | ROLE_USER | Tài khoản người dùng thông thường |

---

## Token Information

- **Thuật toán**: HS256 (HMAC with SHA-256)
- **Thời gian hết hạn**: 24 giờ (86400000 ms)
- **Header format**: `Authorization: Bearer {token}`
- **Token chứa**: username, authorities, issued time, expiration time

---

## API Response Codes

| Code | Meaning | Description |
|------|---------|-------------|
| 200 | OK | Request thành công |
| 201 | Created | Tạo resource thành công |
| 400 | Bad Request | Dữ liệu request không hợp lệ |
| 401 | Unauthorized | Chưa đăng nhập hoặc token không hợp lệ |
| 403 | Forbidden | Không có quyền truy cập |
| 404 | Not Found | Resource không tồn tại |
| 500 | Internal Server Error | Lỗi server |

---

## Tips & Best Practices

1. **Luôn kiểm tra token expiration**: Token có thời hạn 24h, sau đó cần login lại
2. **Sử dụng HTTPS trong production**: Bảo vệ token khỏi bị đánh cắp
3. **Không hardcode token**: Sử dụng biến môi trường hoặc secure storage
4. **Refresh token**: Implement refresh token mechanism cho production
5. **Rate limiting**: Giới hạn số lần request để tránh abuse

---

**Happy Testing! 🚀**
