# Equix Technologies Homework

---
### Khởi chạy ứng dụng

Yêu cầu: Java 17+ Maven 3.8+

**Clone repository**

git clone [https://github.com/19longdt/EquixTechHw.git](https://github.com/19longdt/EquixTechHw.git)
cd EquixTechHw

default bracnh: `master`

---

### Chạy ứng dụng

Default log: debug, có thể chạy info nếu không quan tâm đến chi tiết xử lý

`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dlogging.level.root=info"`

### Đường dẫn truy cập

- **Trang chính**: [http://localhost:8080](http://localhost:8080/)
- **Swagger API Docs**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

> (Swagger đã được thêm sẵn component basicAuth)
>

### Tài khoản mẫu

Admin Account

> Username: admin | Password: 111qqq
>

User Account
> Username: user | Password: 111222
>
---

### Chạy Test

Sử dụng lệnh sau để chạy toàn bộ test case:

`mvn test`

**Postman** [API collection](./Equix-Homework.postman_collection.json)
(./Equix-Homework.postman_collection.json)

---

### Các phần xử lý thêm

1. Xử lý tuần tự với `synchronized` Xử lý tuần tự create/cancel/… order.

2. API hỗ trợ phân trang: `orders-paging` theo phân trang, phục vụ yêu cầu người dùng thực thế.

---

### **Test Cases**

1. `createOrder_shouldSuccess`: Test tạo order thành công.
2. `cancelOrder_invalidStatus_shouldThrow`: Test hủy order với trạng thái không hợp lệ.
3. `testSimulateMatching_shouldUpdateSomeOrders`: Mô phỏng tính năng khớp lệnh dành cho user role `admin`.
4. `cancelOrder_success` : Huỷ order thành công
5. `testSynchronizedCreateOrder_parallelCalls`: Kiểm tra tính đúng đắn của `synchronized`:
- Mô phỏng tạo 5 orders với 5 threads.
- **Kỳ vọng**: Thời gian thực tế gần 5s nếu `synchronized` hoạt động đúng (5 threads, mỗi thread create order mặc định sleep 1s).
