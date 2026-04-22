# BÀI 4 – TỐI ƯU HÓA CẤU TRÚC GIAO DIỆN (DRY)

## 1. Mục tiêu

* Hiểu nguyên tắc DRY (Don't Repeat Yourself)
* So sánh giữa việc lặp code và sử dụng Layout Dialect
* Áp dụng Thymeleaf Layout Dialect để tái sử dụng giao diện

---

## 2. Bối cảnh

Hệ thống có nhiều trang (Danh sách món ăn, Chi tiết món ăn) sử dụng chung:

* Header (logo nhà hàng)
* Footer (thông tin liên hệ)

Nếu copy-paste vào từng file sẽ gây lặp code và khó bảo trì.

---

## 3. So sánh 2 cách tiếp cận

| Tiêu chí    | (A) Copy-Paste Header/Footer | (B) Layout Dialect |
| ----------- | ---------------------------- | ------------------ |
| Tái sử dụng | ❌ Thấp                       | ✅ Cao              |
| Bảo trì     | ❌ Khó (sửa nhiều nơi)        | ✅ Dễ (sửa 1 nơi)   |
| Nguy cơ lỗi | ❌ Cao                        | ✅ Thấp             |
| Code sạch   | ❌ Vi phạm DRY                | ✅ Tuân thủ DRY     |
| Hiệu năng   | ✅ Nhẹ hơn                    | ⚠️ Có overhead nhỏ |
| Độ phức tạp | ✅ Dễ                         | ❌ Cần học thêm     |

### Kết luận:

* Dự án nhỏ → có thể dùng Copy-Paste
* Dự án thực tế → nên dùng Layout Dialect

---

## 4. Cấu trúc thư mục

```id="r3s8xk"
WEB-INF/templates/
 ├── layout/
 │    └── main-layout.html
 ├── dish-list.html
 └── dish-detail.html
```

---

## 5. Triển khai Layout

### 📄 main-layout.html

```html id="d2l9fz"
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">

<head>
    <meta charset="UTF-8">
    <title layout:title-pattern="$CONTENT_TITLE - Restaurant">Restaurant</title>
</head>

<body>

<header>
    <h1>🍽️ Nhà hàng ABC</h1>
    <hr/>
</header>

<div layout:fragment="content">
    <!-- nội dung động -->
</div>

<footer>
    <hr/>
    <p>Liên hệ: 0123 456 789</p>
</footer>

</body>
</html>
```

---

### 📄 dish-list.html

```html id="9f4zq1"
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="layout/main-layout">

<head>
    <title>Danh sách món ăn</title>
</head>

<body>

<div layout:fragment="content">
    <h2>Danh sách món ăn</h2>

    <table border="1">
        <tr>
            <th>Tên</th>
            <th>Giá</th>
        </tr>

        <tr th:each="dish : ${dishes}">
            <td th:text="${dish.name}"></td>
            <td th:text="${dish.price}"></td>
        </tr>
    </table>
</div>

</body>
</html>
```

---

### 📄 dish-detail.html

```html id="w6c2mk"
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="layout/main-layout">

<head>
    <title>Chi tiết món ăn</title>
</head>

<body>

<div layout:fragment="content">
    <h2>Chi tiết món ăn</h2>

    <p>Tên: <span th:text="${dish.name}"></span></p>
    <p>Giá: <span th:text="${dish.price}"></span></p>
</div>

</body>
</html>
```

---

## 6. Cấu hình Spring (Quan trọng)

### Thêm LayoutDialect vào TemplateEngine

```java id="z1x9a7"
@Bean
public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setTemplateResolver(templateResolver());

    engine.addDialect(new LayoutDialect());

    return engine;
}
```

---

## 7. Giải thích kỹ thuật

Thymeleaf mặc định chỉ hỗ trợ Standard Dialect.

Các thuộc tính như:

* layout:decorate
* layout:fragment

thuộc về Layout Dialect (thư viện mở rộng).

Nếu không đăng ký:

```java id="s0p9lm"
engine.addDialect(new LayoutDialect());
```

thì:

* Thymeleaf sẽ không hiểu các thuộc tính layout
* Giao diện sẽ không render đúng

---

## 8. Kết quả đạt được

* Loại bỏ lặp code (Header/Footer)
* Tái sử dụng layout cho nhiều trang
* Code rõ ràng, dễ bảo trì
* Áp dụng nguyên tắc DRY

---

## 9. Kết luận

* Layout Dialect giúp tổ chức giao diện chuyên nghiệp hơn
* Cần đánh đổi một chút độ phức tạp để đạt khả năng mở rộng
* Đây là cách tiếp cận phổ biến trong các dự án thực tế

---
