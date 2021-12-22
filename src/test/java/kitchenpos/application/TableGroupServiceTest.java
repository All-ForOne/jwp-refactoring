package kitchenpos.application;

import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TableGroupServiceTest {
    @Mock
    OrderDao orderDao;

    @Mock
    OrderTableDao orderTableDao;

    @Mock
    TableGroupDao tableGroupDao;

    @InjectMocks
    TableGroupService tableGroupService;

    @DisplayName("테이블 그룹을 생성한다.")
    @Test
    void create() {
        // given
        OrderTable orderTable = OrderTable.of(1L, null, 2, true);
        OrderTable orderTable2 = OrderTable.of(2L, null, 3, true);
        List<OrderTable> orderTables = Arrays.asList(orderTable, orderTable2);
        TableGroup tableGroup = TableGroup.of(1L, null, orderTables);
        when(orderTableDao.findAllByIdIn(any())).thenReturn(orderTables);
        when(tableGroupDao.save(tableGroup)).thenReturn(tableGroup);
        when(orderTableDao.save(orderTable)).thenReturn(orderTable);
        when(orderTableDao.save(orderTable2)).thenReturn(orderTable2);

        // when
        TableGroup expected = tableGroupService.create(tableGroup);

        // then
        assertThat(tableGroup.getId()).isEqualTo(expected.getId());
    }

    @DisplayName("오더테이블이 없거나 오더테이블이 하나인 테이블 그룹을 생성한다.")
    @Test
    void create2() {
        // given
        OrderTable orderTable = OrderTable.of(1L, null, 2, false);
        TableGroup 하나_오더테이블_테이블_그룹 = TableGroup.of(1L, null, Arrays.asList(orderTable));
        TableGroup 빈_오더테이블_테이블_그룹 = TableGroup.of(1L, null, null);

        //then
        assertAll(
                () -> assertThatThrownBy(
                        () -> tableGroupService.create(하나_오더테이블_테이블_그룹)
                ).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(
                        () -> tableGroupService.create(빈_오더테이블_테이블_그룹)
                ).isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("주문테이블에 테이블 그룹을 해제한다.")
    @Test
    void ungroup() {
        // given
        OrderTable orderTable = OrderTable.of(1L, 1L, 2, false);
        OrderTable orderTable2 = OrderTable.of(2L, 1L, 3, false);
        List<OrderTable> orderTables = Arrays.asList(orderTable, orderTable2);
        TableGroup tableGroup = TableGroup.of(1L, null, orderTables);
        List<String> orderStatusList = Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name());
        when(orderTableDao.findAllByTableGroupId(any())).thenReturn(orderTables);
        when(orderDao.existsByOrderTableIdInAndOrderStatusIn(Arrays.asList(1L, 2L), orderStatusList)).thenReturn(false);
        orderTable.setTableGroupId(null);
        orderTable2.setTableGroupId(null);
        when(orderTableDao.save(orderTable)).thenReturn(orderTable);
        when(orderTableDao.save(orderTable2)).thenReturn(orderTable2);

        // when
        tableGroupService.ungroup(tableGroup.getId());


        // then
        assertThat(orderTable.getTableGroupId()).isNull();
    }

    @DisplayName("오더상태가 조리중이거나 요리중인 주문테이블에 테이블 그룹을 해제한다.")
    @Test
    void ungroup2() {
        // given
        OrderTable orderTable = OrderTable.of(1L, 1L, 2, false);
        OrderTable orderTable2 = OrderTable.of(2L, 1L, 3, false);
        List<OrderTable> orderTables = Arrays.asList(orderTable, orderTable2);
        TableGroup tableGroup = TableGroup.of(1L, null, orderTables);
        when(orderTableDao.findAllByTableGroupId(any())).thenReturn(orderTables);
        List<String> orderStatusList = Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name());
        when(orderDao.existsByOrderTableIdInAndOrderStatusIn(Arrays.asList(1L, 2L), orderStatusList)).thenReturn(true);

        // then
        assertThatThrownBy(
                () -> tableGroupService.ungroup(tableGroup.getId())
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
