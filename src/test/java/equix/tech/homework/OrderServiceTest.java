package equix.tech.homework;

import equix.tech.homework.adapter.dto.CommonResponse;
import equix.tech.homework.adapter.dto.order.OrderCreateRequest;
import equix.tech.homework.adapter.dto.order.OrderResponse;
import equix.tech.homework.adapter.dto.paging.PageRequest;
import equix.tech.homework.domain.exception.InvalidException;
import equix.tech.homework.domain.exception.NotFoundException;
import equix.tech.homework.application.service.AdminService;
import equix.tech.homework.application.service.OrderService;
import equix.tech.homework.application.service.impl.AdminServiceImpl;
import equix.tech.homework.application.service.impl.OrderServiceImpl;
import equix.tech.homework.domain.enums.OrderSide;
import equix.tech.homework.domain.enums.OrderStatus;
import equix.tech.homework.domain.model.Order;
import equix.tech.homework.domain.repository.impl.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTest {
    private static final Logger log = LoggerFactory.getLogger("equix.OrderServiceTest");
    private OrderService orderService;
    private AdminService adminService;
    private InMemoryOrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        orderService = new OrderServiceImpl(orderRepository);
        adminService = new AdminServiceImpl(orderRepository);
    }

    @Test
    void createOrder_shouldSuccess() {
        OrderCreateRequest request = new OrderCreateRequest(
            "FPT", BigDecimal.valueOf(100), 100, OrderSide.BUY
        );

        CommonResponse response = orderService.createOrder(request);
        OrderResponse order = (OrderResponse) response.getData();

        assertNotNull(order.getId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals("FPT", order.getSymbol());
        assertEquals(100, order.getQuantity());
    }

    @Test
    void cancelOrder_success() {
        OrderCreateRequest request = new OrderCreateRequest(
            "VIC", BigDecimal.valueOf(80), 50, OrderSide.SELL
        );
        OrderResponse order = (OrderResponse) orderService.createOrder(request).getData();

        orderService.cancelOrder(order.getId());

        Optional<Order> updated = orderRepository.findById(order.getId());
        assertTrue(updated.isPresent());
        assertEquals(OrderStatus.CANCELLED, updated.get().getStatus());
    }

    @Test
    void cancelOrder_invalidStatus_shouldThrow() {
        OrderCreateRequest request = new OrderCreateRequest(
            "VCB", BigDecimal.valueOf(80), 10, OrderSide.BUY
        );
        OrderResponse created = (OrderResponse) orderService.createOrder(request).getData();

        // Update status directly in repository
        Order order = orderRepository.findById(created.getId()).get();
        order.setStatus(OrderStatus.EXECUTED);
        orderRepository.update(List.of(order));

        InvalidException ex = assertThrows(InvalidException.class,
            () -> orderService.cancelOrder(created.getId()));

        assertTrue(ex.getMessage().contains("Only PENDING orders can be cancelled."));
    }

    @Test
    void testSimulateMatching_shouldUpdateSomeOrders() {
        int sizeProcess = 10;
        for (int i = 0; i < sizeProcess; i++) {
            OrderCreateRequest req = new OrderCreateRequest(
                "MBB", BigDecimal.valueOf(24), 10 + i, OrderSide.BUY
            );
            orderService.createOrder(req);
        }

        try {
            adminService.simulateMatching();
        } catch (NotFoundException ex) {
            log.error("[SimulateMatching] No orders were matched (all false): acceptable.");
            return;
        }

        // Check status
        CommonResponse response = orderService.getOrders(new PageRequest(0, sizeProcess));
        List<OrderResponse> allItems = (List<OrderResponse>) response.getData();
        long executedCount = allItems.stream().filter(o -> o.getStatus() == OrderStatus.EXECUTED).count();
        long pendingCount = allItems.stream().filter(o -> o.getStatus() == OrderStatus.PENDING).count();
        log.info("[AfterSimulateMatching] Pending orders: {}, executed orders: {}.", pendingCount, executedCount);

        assertEquals(sizeProcess, executedCount + pendingCount);
    }

    @Test
    void testSynchronizedCreateOrder_parallelCalls() throws InterruptedException {
        int threadCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        CountDownLatch latch = new CountDownLatch(threadCount);
        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            int finalIndex = i;
            executor.submit(() -> {
                try {
                    log.info("[ParallelCalls] Thread-{} START at {}", finalIndex, System.currentTimeMillis());
                    OrderCreateRequest req = new OrderCreateRequest(
                        "FPT", BigDecimal.valueOf(100), 10 + finalIndex + finalIndex, OrderSide.BUY
                    );
                    orderService.createOrder(req);
                    log.info("[ParallelCalls] Thread-{} END at {}", finalIndex, System.currentTimeMillis());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // Pending threads completed
        long end = System.currentTimeMillis();
        log.info("[ParallelCalls] Total execution time: {} ms", (end - start));
        executor.shutdown();

        // Kỳ vọng thời gian thực tế gần 5s nếu synchronized hoạt động (5 threads, mỗi thread sleep 1s)
        assertTrue((end - start) >= threadCount * 1000);
    }
}
