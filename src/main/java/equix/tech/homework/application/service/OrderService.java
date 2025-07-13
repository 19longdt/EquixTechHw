package equix.tech.homework.application.service;

import equix.tech.homework.application.dto.CommonResponse;
import equix.tech.homework.application.dto.paging.PageRequest;
import equix.tech.homework.application.dto.order.OrderCreateRequest;

public interface OrderService {
    CommonResponse createOrder(OrderCreateRequest request);

    CommonResponse getAllOrders();

    CommonResponse getOrders(PageRequest request);

    CommonResponse getOrderById(Long id);

    void cancelOrder(Long id);
}
