package equix.tech.homework.application.service.impl;

import equix.tech.homework.domain.exception.NotFoundException;
import equix.tech.homework.application.service.AdminService;
import equix.tech.homework.common.Utils;
import equix.tech.homework.domain.enums.OrderStatus;
import equix.tech.homework.domain.model.Order;
import equix.tech.homework.domain.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger log = LoggerFactory.getLogger("equix.AdminServiceImpl");
    private final OrderRepository orderRepository;

    public AdminServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public synchronized void simulateMatching() {
        List<Order> pendingOrders = orderRepository.findAll().stream()
            .filter(item -> Objects.equals(item.getStatus(), OrderStatus.PENDING))
            .toList();
        log.debug("[Before] current pending orders {}", Utils.toJsonString(pendingOrders));

        List<Order> executeOrders = new ArrayList<>();
        Random randomGenerator = new Random();
        for (Order item : pendingOrders) {
            boolean shouldExecute = randomGenerator.nextBoolean();
            if (shouldExecute) {
                log.debug("[Change] id {}, status before {} after {}", item.getId(), item.getStatus(), OrderStatus.EXECUTED);
                item.setStatus(OrderStatus.EXECUTED);
                executeOrders.add(item);
            }
        }
        if (executeOrders.isEmpty()) {
            throw new NotFoundException(OrderStatus.PENDING);
        }
        orderRepository.update(executeOrders);
        log.debug("[After] current pending orders {}", Utils.toJsonString(pendingOrders));
    }
}
