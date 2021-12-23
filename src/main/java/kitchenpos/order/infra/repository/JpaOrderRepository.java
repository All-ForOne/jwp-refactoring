package kitchenpos.order.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, Long> {
}
