package kitchenpos.table.repository;

import kitchenpos.table.domain.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTableRepository extends JpaRepository<OrderTable, Long> {
    List<OrderTable> findAllByIdIn(List<Long> orderTableIds);
}