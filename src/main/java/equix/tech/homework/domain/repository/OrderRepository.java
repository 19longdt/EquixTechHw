package equix.tech.homework.domain.repository;

import equix.tech.homework.application.dto.paging.PageRequest;
import equix.tech.homework.application.dto.paging.PagedResult;
import equix.tech.homework.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    PagedResult<Order> findOrdersPaging(PageRequest request);

    void update(List<Order> orders);
}
