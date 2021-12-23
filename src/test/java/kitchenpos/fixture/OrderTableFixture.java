package kitchenpos.fixture;

import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.dto.OrderTableRequest;

public class OrderTableFixture {

    public static OrderTableRequest 샘플_Request(){
        OrderTableRequest sample = new OrderTableRequest();
        sample.setEmpty(true);
        sample.setNumberOfGuests(0);
        return sample;
    }

    public static OrderTableRequest 생성_Request(int numberOfGuests, boolean empty){
        OrderTableRequest orderTableRequest = new OrderTableRequest();
        orderTableRequest.setNumberOfGuests(numberOfGuests);
        orderTableRequest.setEmpty(empty);
        return orderTableRequest;
    }

    public static OrderTable 생성(int numberOfGuests, boolean empty) {
        return new OrderTable(numberOfGuests, empty);
    }
}