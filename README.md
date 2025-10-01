# Spring Security 6 Demo với Spring Boot

Dự án demo đầy đủ các tính năng bảo mật của **Spring Security 6** sử dụng **Spring Boot 3.5.6**, **Spring Data JPA**, **H2 Database** và giao diện **Thymeleaf** kết hợp **Bootstrap 5**.

## 🚀 Tính năng

### 🔐 Spring Security 6
- **Form-based Authentication**: Đăng nhập qua form với username/password
- **Role-based Authorization**: Phân quyền dựa trên vai trò (ROLE_USER, ROLE_ADMIN)
- **Password Encryption**: Mã hóa mật khẩu với BCrypt
- **CSRF Protection**: Bảo vệ chống tấn công CSRF
- **Session Management**: Quản lý phiên đăng nhập
- **Custom Login/Logout**: Trang đăng nhập và đăng xuất tùy chỉnh
- **Access Denied Handling**: Xử lý truy cập bị từ chối

### 💾 Database & JPA
- **H2 In-Memory Database**: Database trong bộ nhớ cho việc demo
- **Spring Data JPA**: Truy xuất dữ liệu dễ dàng
- **Entity Relationships**: Quan hệ Many-to-Many giữa User và Role
- **Data Initialization**: Tự động tạo dữ liệu mẫu khi khởi động

### 🎨 Giao diện
- **Thymeleaf Template Engine**: Render HTML động
- **Bootstrap 5**: Giao diện đẹp, responsive
- **Bootstrap Icons**: Biểu tượng hiện đại
- **Thymeleaf Security Integration**: Tích hợp với Spring Security để hiển thị/ẩn nội dung

## 📋 Yêu cầu

- **Java**: 17 hoặc cao hơn
- **Maven**: 3.6+ (hoặc sử dụng Maven Wrapper đi kèm)
- **IDE**: IntelliJ IDEA, Eclipse, hoặc VS Code

## 🛠️ Cài đặt và chạy

### 1. Clone hoặc giải nén dự án

```bash
cd BT_WEB_09
```

### 2. Chạy ứng dụng

#### Sử dụng Maven Wrapper (khuyến nghị)

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

#### Hoặc sử dụng Maven đã cài đặt

```bash
mvn spring-boot:run
```

### 3. Truy cập ứng dụng

Mở trình duyệt và truy cập: **http://localhost:8080**

## 👥 Tài khoản mặc định

Hệ thống tự động tạo 2 tài khoản khi khởi động:

| Vai trò | Username | Password | Quyền truy cập |
|---------|----------|----------|----------------|
| **Admin** | `admin` | `admin123` | Toàn quyền quản trị hệ thống |
| **User** | `user` | `user123` | Quyền người dùng thông thường |

## 📁 Cấu trúc dự án

```
BT_WEB_09/
├── src/main/java/com/dev/nhatnguyen/
│   ├── config/                    # Cấu hình
│   │   ├── SecurityConfig.java    # Cấu hình Spring Security
│   │   └── DataInitializer.java   # Khởi tạo dữ liệu mẫu
│   ├── controller/                # Controllers
│   │   ├── HomeController.java
│   │   ├── AuthController.java
│   │   ├── UserController.java
│   │   └── AdminController.java
│   ├── dto/                       # Data Transfer Objects
│   │   └── UserRegistrationDto.java
│   ├── entity/                    # JPA Entities
│   │   ├── User.java
│   │   └── Role.java
│   ├── repository/                # Spring Data JPA Repositories
│   │   ├── UserRepository.java
│   │   └── RoleRepository.java
│   ├── security/                  # Security classes
│   │   └── CustomUserDetails.java
│   ├── service/                   # Services
│   │   ├── CustomUserDetailsService.java
│   │   └── UserService.java
│   └── BtWeb09Application.java    # Main class
├── src/main/resources/
│   ├── templates/                 # Thymeleaf templates
│   │   ├── fragments/             # Fragments (header, navbar, footer)
│   │   ├── admin/                 # Admin pages
│   │   ├── user/                  # User pages
│   │   ├── index.html
│   │   ├── login.html
│   │   ├── register.html
│   │   ├── home.html
│   │   └── access-denied.html
│   └── application.properties     # Cấu hình ứng dụng
└── pom.xml                        # Maven dependencies
```

## 🔗 Các URL chính

| URL | Mô tả | Quyền truy cập |
|-----|-------|----------------|
| `/` | Trang chủ | Public |
| `/login` | Đăng nhập | Public |
| `/register` | Đăng ký | Public |
| `/home` | Dashboard chung | Authenticated |
| `/user/dashboard` | Dashboard người dùng | ROLE_USER |
| `/user/profile` | Hồ sơ người dùng | ROLE_USER |
| `/admin/dashboard` | Dashboard quản trị | ROLE_ADMIN |
| `/admin/users` | Quản lý người dùng | ROLE_ADMIN |
| `/h2-console` | H2 Database Console | Public (trong demo) |
| `/access-denied` | Trang lỗi 403 | Public |

## 🗄️ Truy cập H2 Database Console

1. Truy cập: **http://localhost:8080/h2-console**
2. Cấu hình kết nối:
   - **JDBC URL**: `jdbc:h2:mem:securitydb`
   - **Username**: `sa`
   - **Password**: (để trống)
3. Click **Connect**

## 📚 Các tính năng demo

### 1. Xác thực (Authentication)
- ✅ Đăng nhập với username/password
- ✅ Đăng ký tài khoản mới
- ✅ Đăng xuất
- ✅ Ghi nhớ đăng nhập
- ✅ Thông báo lỗi khi đăng nhập sai

### 2. Phân quyền (Authorization)
- ✅ Phân quyền theo vai trò (Role-based)
- ✅ Bảo vệ URL theo role
- ✅ Hiển thị/ẩn menu theo quyền
- ✅ Trang Access Denied (403)
- ✅ Chỉ Admin mới quản lý được users

### 3. Bảo mật
- ✅ Mã hóa mật khẩu với BCryptPasswordEncoder
- ✅ CSRF Protection
- ✅ Session Management
- ✅ Secure Headers
- ✅ XSS Protection

### 4. Quản lý người dùng (Admin)
- ✅ Xem danh sách người dùng
- ✅ Xóa người dùng
- ✅ Thống kê người dùng
- ✅ Truy cập H2 Console

## 🎓 Học từ dự án này

Dự án này demo các khái niệm quan trọng:

1. **Spring Security 6 Configuration**: Cách cấu hình bảo mật hiện đại với SecurityFilterChain
2. **UserDetailsService**: Tùy chỉnh cách load thông tin user
3. **PasswordEncoder**: Mã hóa mật khẩu an toàn
4. **Method Security**: Bảo vệ method với `@PreAuthorize`, `@Secured`
5. **Thymeleaf Security**: Sử dụng `sec:authorize` trong template
6. **JPA Relationships**: Quan hệ Many-to-Many
7. **DTO Pattern**: Sử dụng DTO cho form submission
8. **Spring Boot Auto-configuration**: Tận dụng tính năng tự động cấu hình

## 🔧 Tuỳ chỉnh

### Thay đổi port

Sửa trong `application.properties`:
```properties
server.port=8081
```

### Thay đổi database sang MySQL

1. Thêm dependency trong `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

2. Sửa `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/securitydb
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Tắt auto-login sau khi đăng ký

Trong `AuthController.java`, thay đổi return về `/login` thay vì tự động redirect.

## 📝 License

Dự án này được tạo ra cho mục đích học tập và demo. Bạn có thể tự do sử dụng và chỉnh sửa.

## 👨‍💻 Tác giả

Phát triển bởi **Nhat Nguyen** - Demo Spring Security 6

## 🤝 Đóng góp

Mọi đóng góp đều được chào đón! Hãy tạo Pull Request hoặc mở Issue nếu bạn tìm thấy lỗi.

---

**Chúc bạn học tập và phát triển tốt với Spring Security! 🚀**
