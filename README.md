# BÀI 1 – PHÁ ÁN LỖI KẾT XUẤT GIAO DIỆN (THYMELEAF)

## 1. Mô tả bài toán

Dự án ban đầu sử dụng JSP, sau đó lập trình viên cấu hình thêm Thymeleaf nhưng sai cách, dẫn đến:

* Lỗi HTTP 500 hoặc 404
* Giao diện không hiển thị

---

## 2. Phân tích lỗi cấu hình

### ❌ Lỗi 1: Sai thư mục template (prefix)

```java
resolver.setPrefix("/WEB-INF/views");
```

* Sai vì Thymeleaf mặc định sử dụng thư mục:

```
/WEB-INF/templates/
```

---

### ❌ Lỗi 2: Sai định dạng file (suffix)

```java
resolver.setSuffix(".jsp");
```

* Sai vì Thymeleaf không xử lý JSP
* Đúng phải là:

```
.html
```

---

## 3. Nguyên nhân

* Thymeleaf là template engine xử lý file HTML
* JSP là công nghệ khác, không tương thích trực tiếp với Thymeleaf
* Việc cấu hình sai prefix và suffix khiến hệ thống không tìm thấy view hợp lệ

---

## 4. Cách khắc phục

### ✅ Cấu hình đúng `SpringResourceTemplateResolver`

```java
resolver.setPrefix("/WEB-INF/templates/");
resolver.setSuffix(".html");
resolver.setCharacterEncoding("UTF-8");
```

---

### ✅ Cấu hình đầy đủ trong `WebConfig.java`

```java
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.restaurant.bai1")
public class WebConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();

        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");

        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
}
```

---

### ✅ Thêm `AppInit` để khởi tạo DispatcherServlet

```java
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
```

---

## 5. Kết quả sau khi sửa

* Ứng dụng chạy thành công trên Tomcat
* Truy cập: `http://localhost:8080/`
* Hiển thị nội dung từ file `home.html`
* Thymeleaf render đúng dữ liệu

---

## 6. Kết luận

* Cần phân biệt rõ giữa JSP và Thymeleaf
* Cấu hình ViewResolver phải phù hợp với công nghệ sử dụng
* Sai prefix hoặc suffix sẽ khiến hệ thống không tìm được view

---
