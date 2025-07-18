package equix.tech.homework.adapter.web.controller;

import equix.tech.homework.adapter.dto.CommonResponse;
import equix.tech.homework.adapter.dto.paging.PageRequest;
import equix.tech.homework.adapter.dto.order.OrderCreateRequest;
import equix.tech.homework.application.service.OrderService;
import equix.tech.homework.common.Utils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger("equix.OrderController");
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<CommonResponse> create(@Valid @RequestBody OrderCreateRequest request) {
        log.debug("[Create] request {}", Utils.toJsonString(request));
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(request));
    }

    @GetMapping("/orders")
    public ResponseEntity<CommonResponse> getAllOrders() {
        log.debug("[GetAll]");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<CommonResponse> getDetailOrders(@NotNull @PathVariable Long id) {
        log.debug("[GetDetail] request id {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }

    @PostMapping("/orders/{id}/cancel")
    public ResponseEntity<CommonResponse> cancelOrder(@NotNull @PathVariable Long id) {
        log.debug("[Cancel] request id {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(id));
    }

    @PostMapping("/orders-paging")
    public ResponseEntity<CommonResponse> getOrders(@Valid @RequestBody PageRequest request) {
        log.debug("[GetPaging] request {}", Utils.toJsonString(request));
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders(request));
    }

}
