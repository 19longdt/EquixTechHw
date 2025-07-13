package equix.tech.homework.application.service.impl;

import equix.tech.homework.application.dto.CommonResponse;
import equix.tech.homework.application.dto.order.OrderCreateRequest;
import equix.tech.homework.application.dto.order.OrderResponse;
import equix.tech.homework.application.dto.paging.PageRequest;
import equix.tech.homework.application.dto.paging.PagedResult;
import equix.tech.homework.application.exception.InvalidException;
import equix.tech.homework.application.exception.NotFoundException;
import equix.tech.homework.application.service.OrderService;
import equix.tech.homework.common.Utils;
import equix.tech.homework.domain.enums.OrderStatus;
import equix.tech.homework.domain.model.Order;
import equix.tech.homework.domain.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static equix.tech.homework.common.Messages.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger("equix.OrderServiceImpl");
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public synchronized CommonResponse createOrder(OrderCreateRequest request) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Order order = new Order();
        order.setSymbol(request.getSymbol());
        order.setPrice(request.getPrice());
        order.setQuantity(request.getQuantity());
        order.setSide(request.getSide());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedTime(ZonedDateTime.now());

        Order saved = orderRepository.save(order);
        log.debug("[Processing] createOrder result: {}", Utils.toJsonString(order));
        return new CommonResponse(ORDER_CREATE, SUCCESS, convertToResponse(saved), 1);
    }

    private OrderResponse convertToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }

    @Override
    public CommonResponse getAllOrders() {
        List<Order> allOrders = orderRepository.findAll();
        List<OrderResponse> response = getOrderResponses(allOrders);
        log.debug("[GetAll] result: {}", Utils.toJsonString(response));
        return new CommonResponse(ORDER_GET_ALL, SUCCESS, response, response.size());
    }

    @Override
    public CommonResponse getOrders(PageRequest request) {
        PagedResult<Order> orders = orderRepository.findOrdersPaging(request);
        List<OrderResponse> response = getOrderResponses(orders.getContent());
        log.debug("[GetPaging] result: {}", Utils.toJsonString(response));
        return new CommonResponse(ORDER_GET_PAGING, SUCCESS, response, orders.getTotalElements());
    }

    private List<OrderResponse> getOrderResponses(List<Order> orders) {
        List<OrderResponse> response = new ArrayList<>();
        for (Order order : orders) {
            OrderResponse itemConvert = convertToResponse(order);
            response.add(itemConvert);
        }
        return response;
    }

    @Override
    public CommonResponse getOrderById(Long id) {
        Order order = findOrThrow(id);
        log.debug("[GetDetail] result: {}", Utils.toJsonString(order));
        return new CommonResponse(ORDER_GET_DETAIL, SUCCESS, convertToResponse(order), 1);
    }

    private Order findOrThrow(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public synchronized void cancelOrder(Long id) {
        Order order = findOrThrow(id);
        if (!Objects.equals(order.getStatus(), OrderStatus.PENDING)) {
            throw new InvalidException(ORDER_INVALID_STATE);
        }
        order.setStatus(OrderStatus.CANCELLED);
        log.debug("[Cancel] result: {}", Utils.toJsonString(order));
        orderRepository.update(List.of(order));
    }
}
