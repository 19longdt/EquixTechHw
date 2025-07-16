package equix.tech.homework.application.service;

import equix.tech.homework.adapter.dto.CommonResponse;
import equix.tech.homework.adapter.dto.paging.PageRequest;
import equix.tech.homework.adapter.dto.order.OrderCreateRequest;

public interface OrderService {
    CommonResponse createOrder(OrderCreateRequest request);

    CommonResponse getAllOrders();

    CommonResponse getOrders(PageRequest request);

    CommonResponse getOrderById(Long id);

    CommonResponse cancelOrder(Long id);
}
