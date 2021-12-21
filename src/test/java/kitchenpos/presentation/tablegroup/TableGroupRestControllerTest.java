package kitchenpos.presentation.tablegroup;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.dto.table.OrderTableDto;
import kitchenpos.dto.tablegroup.TableGroupDto;
import kitchenpos.presentation.table.TableRestControllerTest;
import kitchenpos.testassistance.config.TestConfig;

@DisplayName("단체지정 API기능에 관한")
public class TableGroupRestControllerTest extends TestConfig {
    @DisplayName("단체지정이 저장된다.")
    @Test
    void save_product() {
        // given
        List<OrderTableDto> orderTables = 반테이블들_조회됨();

        TableGroupDto tableGroup = TableGroupDto.of(List.of(orderTables.get(0), orderTables.get(1)));

        // when
        ExtractableResponse<Response> response = 단체지정_저장요청(tableGroup);

        // then
        단체지정_저장됨(response);
    }

    @DisplayName("단체지정이 삭제된다.")
    @Test
    void delete_product() {
        // given
        TableGroupDto deletingTableGroup = 단제치정_생성();

        // when
        ExtractableResponse<Response> response = 단체지정_삭제요청(deletingTableGroup.getId());

        // then
        단체지정_삭제됨(response);
    }

    private TableGroupDto 단제치정_생성() {
        List<OrderTableDto> orderTables = 반테이블들_조회됨();

        TableGroupDto tableGroup = TableGroupDto.of(List.of(orderTables.get(0), orderTables.get(1)));

        return 단체지정_저장요청(tableGroup).as(TableGroupDto.class);
    }
    private List<OrderTableDto> 반테이블들_조회됨() {
        return List.of(TableRestControllerTest.주문테이블_조회요청().as(OrderTableDto[].class)).stream()
                                .filter(OrderTableDto::isEmpty)
                                .collect(Collectors.toList());
    }

    private void 단체지정_저장됨(ExtractableResponse<Response> response) {
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static ExtractableResponse<Response> 단체지정_저장요청(TableGroupDto tableGroup) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tableGroup)
                .when().post("/api/table-groups")
                .then().log().all()
                .extract();
    }

    private void 단체지정_삭제됨(ExtractableResponse<Response> response) {
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> 단체지정_삭제요청(Long tableGroupId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/table-groups/" + tableGroupId)
                .then().log().all()
                .extract();
    }
}
