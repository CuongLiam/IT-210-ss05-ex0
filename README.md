# BÀI 2 – THIẾT KẾ TRANG DANH SÁCH MÓN ĂN ĐỘNG (THYMELEAF)

## 1. Mục tiêu

* Sử dụng Thymeleaf Standard Dialect để hiển thị dữ liệu từ Model
* Áp dụng các biểu thức: th:each, th:if, th:unless
* Xử lý điều kiện hiển thị và dữ liệu rỗng

---

## 2. Thiết kế Model

### 📄 Dish.java

```java
public class Dish {
    private int id;
    private String name;
    private double price;
    private boolean isAvailable;

    public Dish(int id, String name, double price, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
}
```

---

## 3. Phân tích luồng Input / Output

### Input:

* Người dùng truy cập: `/bai2/dishes`

### Xử lý:

* Controller nhận request
* Gọi Service để lấy danh sách món ăn

### Output:

* Controller đưa dữ liệu vào Model (key: "dishes")
* Thymeleaf render dữ liệu ra HTML

### Sơ đồ:

Client → Controller → Service → List<Dish> → Model → View (Thymeleaf) → HTML → Client

---

## 4. Service xử lý dữ liệu

```java
@Service
public class DishService {

    public List<Dish> getAllDishes() {
        return List.of(
                new Dish(1, "Phở bò", 50000, true),
                new Dish(2, "Bún chả", 45000, false),
                new Dish(3, "Cơm tấm", 40000, true)
        );
    }
}
```

---

## 5. Controller

```java
@Controller
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/bai2/dishes")
    public String showDishes(Model model) {
        model.addAttribute("dishes", dishService.getAllDishes());
        return "dish-list";
    }
}
```

---

## 6. View – dish-list.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Danh sách món ăn</title>
</head>
<body>

<h2>Danh sách món ăn</h2>

<!-- Trường hợp danh sách rỗng -->
<p th:if="${dishes == null or #lists.isEmpty(dishes)}">
    Hiện tại nhà hàng đang cập nhật thực đơn, vui lòng quay lại sau
</p>

<!-- Hiển thị bảng nếu có dữ liệu -->
<table border="1" th:unless="${dishes == null or #lists.isEmpty(dishes)}">
    <tr>
        <th>ID</th>
        <th>Tên món</th>
        <th>Giá</th>
        <th>Trạng thái</th>
    </tr>

    <tr th:each="dish : ${dishes}">
        <td th:text="${dish.id}"></td>
        <td th:text="${dish.name}"></td>
        <td th:text="${dish.price}"></td>

        <!-- Hiển thị trạng thái + đổi màu -->
        <td th:text="${dish.available ? 'Còn hàng' : 'Hết hàng'}"
            th:style="${dish.available} ? '' : 'color:red'">
        </td>
    </tr>
</table>

</body>
</html>
```

---

## 7. Xử lý logic hiển thị

### ✔️ Lặp danh sách

```html
th:each="dish : ${dishes}"
```

### ✔️ Kiểm tra rỗng

```html
dishes == null or #lists.isEmpty(dishes)
```

### ✔️ Toán tử điều kiện

```html
${dish.available ? 'Còn hàng' : 'Hết hàng'}
```

### ✔️ Đổi màu khi hết hàng

```html
th:style="${dish.available} ? '' : 'color:red'"
```

---

## 8. Kết quả đạt được

* Hiển thị danh sách món ăn động
* Phân biệt trạng thái còn/hết hàng
* Đổi màu cảnh báo khi hết hàng
* Xử lý trường hợp dữ liệu rỗng

---

## 9. Kết luận

* Thymeleaf giúp render dữ liệu động dễ dàng
* Cần sử dụng đúng các biểu thức điều kiện và vòng lặp
* Xử lý dữ liệu rỗng là yêu cầu quan trọng trong thực tế

---
