# BÀI 3 – XÂY DỰNG MODULE CẬP NHẬT THÔNG TIN THỰC ĐƠN

## 1. Mục tiêu

* Sử dụng Thymeleaf để thực hiện Data Binding
* Áp dụng các biểu thức:

    * `th:object`
    * `th:field`
    * `@{...}` (URL Expression)
* Hiểu luồng dữ liệu từ View → Controller → Service → View

---

## 2. Phân tích luồng dữ liệu (Data Flow)

### Bước 1: Từ View danh sách (Bài 2)

* Người dùng nhấn nút **"Chỉnh sửa"**
* Gửi request:

```
/bai3/edit/{id}
```

---

### Bước 2: Controller xử lý

* Nhận `id` từ URL (`@PathVariable`)
* Gọi Service để tìm món ăn tương ứng

---

### Bước 3: Xử lý dữ liệu

#### ✔ Trường hợp hợp lệ:

* Tìm thấy Dish
* Đưa vào Model với key `"dish"`
* Trả về view `edit-dish.html`

#### ❌ Trường hợp lỗi:

* Không tìm thấy Dish
* Redirect về `/bai2/dishes`
* Gửi thông báo lỗi:

```
"Không tìm thấy món ăn yêu cầu!"
```

---

### Bước 4: View hiển thị

* Thymeleaf nhận object Dish
* Sử dụng:

    * `th:object` → bind object
    * `th:field` → đổ dữ liệu vào input

---

### Sơ đồ tổng thể:

Client
→ click Edit
→ Controller (nhận id)
→ Service (findById)
→ Model (Dish)
→ View (edit form)
→ HTML render

---

## 3. Cấu hình quan trọng (Gradle)

Để Spring nhận diện tên biến `@PathVariable`, cần bật:

```gradle id="6cxn8m"
tasks.withType(JavaCompile) {
    options.compilerArgs += ['-parameters']
}
```

---

## 4. Service

```java id="9r7b5w"
@Service
public class AdminDishService {

    private final List<Dish> dishes = List.of(
            new Dish(1, "Phở bò", 50000, true),
            new Dish(2, "Bún chả", 45000, false),
            new Dish(3, "Cơm tấm", 40000, true)
    );

    public Optional<Dish> findById(int id) {
        return dishes.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }
}
```

---

## 5. Controller

```java id="yq6g1p"
@Controller
public class AdminDishController {

    @Autowired
    private AdminDishService service;

    @GetMapping("/bai3/edit/{id}")
    public String editDish(@PathVariable int id, Model model, RedirectAttributes ra) {

        var dishOpt = service.findById(id);

        if (dishOpt.isEmpty()) {
            ra.addFlashAttribute("error", "Không tìm thấy món ăn yêu cầu!");
            return "redirect:/bai2/dishes";
        }

        model.addAttribute("dish", dishOpt.get());
        return "edit-dish";
    }
}
```

---

## 6. View – edit-dish.html

```html id="w7g2fz"
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa món ăn</title>
</head>
<body>

<h2>Chỉnh sửa món ăn</h2>

<form th:object="${dish}">

    <p>
        ID:
        <input type="text" th:field="*{id}" readonly />
    </p>

    <p>
        Tên món:
        <input type="text" th:field="*{name}" />
    </p>

    <p>
        Giá:
        <input type="number" th:field="*{price}" />
    </p>

    <p>
        Trạng thái:
        <input type="checkbox" th:field="*{available}" /> Còn hàng
    </p>

    <button type="submit">Lưu</button>

</form>

</body>
</html>
```

---

## 7. Cập nhật View Bài 2 (dish-list.html)

### Thêm cột hành động:

```html id="h7g8z3"
<th>Hành động</th>
```

---

### Thêm nút chỉnh sửa:

```html id="q3r8vx"
<td>
    <a th:href="@{|/bai3/edit/${dish.id}|}">Chỉnh sửa</a>
</td>
```

---

## 8. Giải thích các biểu thức Thymeleaf

### ✔ th:object

```html id="c0a9pl"
<form th:object="${dish}">
```

→ Liên kết object từ Model với form

---

### ✔ th:field

```html id="8yz0cj"
th:field="*{name}"
```

→ Tự động:

* gán value
* gán name
* hỗ trợ binding 2 chiều

---

### ✔ URL Expression

```html id="a2l9dn"
@{|/bai3/edit/${dish.id}|}
```

→ Tạo URL động theo ID

---

## 9. Kết quả đạt được

* Điều hướng từ danh sách → trang chỉnh sửa
* Tự động điền dữ liệu vào form
* Xử lý lỗi khi ID không tồn tại
* Sử dụng đúng Data Binding của Thymeleaf

---

## 10. Kết luận

* Thymeleaf hỗ trợ binding dữ liệu mạnh mẽ
* Cần hiểu rõ luồng dữ liệu trong MVC
* Việc xử lý lỗi và redirect là rất quan trọng trong ứng dụng thực tế

---
