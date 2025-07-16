package equix.tech.homework.domain.repository.impl;


import equix.tech.homework.adapter.dto.paging.PageRequest;
import equix.tech.homework.adapter.dto.paging.PagedResult;
import equix.tech.homework.domain.model.Order;
import equix.tech.homework.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(idGenerator.getAndIncrement());
        }
        storage.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Order> findAll() {
        return storage.values().stream()
            .sorted(Comparator.comparing(Order::getId).reversed())
            .toList();
    }

    /**
     * @return data paging
     * ex: page = 1, size = 10 -> .skip(10).limit(10)
     *     page = 0, size = 5 -> .skip(0).limit(5)
     */
    @Override
    public PagedResult<Order> findOrdersPaging(PageRequest request) {
        Integer size = request.getSize();
        Integer totalItems = storage.size();
        List<Order> orders = storage.values().stream()
            .sorted(Comparator.comparing(Order::getId).reversed())
            .skip((long) request.getPage() * size)
            .limit(size)
            .toList();
        return new PagedResult<>(orders, totalItems);
    }

    @Override
    public void update(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return;
        }

        Map<Long, Order> tempMap = new ConcurrentHashMap<>();
        for (Order order : orders) {
            if (order != null && order.getId() != null) {
                tempMap.put(order.getId(), order);
            }
        }

        storage.putAll(tempMap);
    }
}
