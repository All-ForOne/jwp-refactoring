package kitchenpos.menu;

import kitchenpos.menu.application.MenuService;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menu.dto.MenuResponse;
import kitchenpos.menuGroup.domain.MenuGroup;
import kitchenpos.menuGroup.domain.MenuGroupRepository;
import kitchenpos.product.domain.Price;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static kitchenpos.menuGroup.MenuGroupServiceTest.메뉴_그룹_등록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("메뉴 관련 기능")
public class MenuServiceTest {
    @Mock
    MenuRepository menuRepository;

    @Mock
    MenuGroupRepository menuGroupRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    MenuService menuService;

    private Product 짜장면;
    private Product 탕수육;
    private MenuGroup 중국음식;
    private Menu 짜장면메뉴;
    private MenuRequest 짜장면메뉴등록요청;

    @BeforeEach
    void setUp() {
        짜장면 = mock(Product.class);
        when(짜장면.getId()).thenReturn(1L);
        when(짜장면.getPrice()).thenReturn(Price.of(new BigDecimal(5000)));

        탕수육 = mock(Product.class);
        when(탕수육.getId()).thenReturn(2L);
        when(탕수육.getPrice()).thenReturn(Price.of(new BigDecimal(15000)));

        중국음식 = 메뉴_그룹_등록("중국음식");
        짜장면메뉴 = 메뉴_등록(1L, "짜장면탕수육세트", 짜장면.getPrice().add(탕수육.getPrice()).getPrice(), 중국음식.getId(), Arrays.asList(메뉴_상품_등록(짜장면, 1L), 메뉴_상품_등록(탕수육, 1L)));
        짜장면메뉴등록요청 = new MenuRequest("짜장면탕수육세트", 짜장면.getPrice().add(탕수육.getPrice()).getPrice(), 중국음식.getId(), Arrays.asList(new MenuProductRequest(짜장면.getId(), 1l), new MenuProductRequest(탕수육.getId(), 1l)));
    }

    @Test
    @DisplayName("메뉴를 등록한다.")
    void createMenu() {
        // given
        given(menuGroupRepository.findById(any())).willReturn(Optional.ofNullable(중국음식));
        given(productRepository.findById(짜장면.getId())).willReturn(Optional.of(짜장면));
        given(productRepository.findById(탕수육.getId())).willReturn(Optional.of(탕수육));
        given(menuRepository.save(any())).willReturn(짜장면메뉴);

        // when
        MenuResponse createMenu = menuService.create(짜장면메뉴등록요청);

        // then
        assertThat(createMenu).isNotNull();
        assertThat(createMenu.getMenuProducts()).hasSize(2);
    }

    public static Menu 메뉴_등록(Long id, String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        Menu menu = new Menu(id, name, price, menuGroupId);
        menu.organizeMenu(menuProducts);
        return menu;
    }

    public static MenuProduct 메뉴_상품_등록(Product product, long quantity) {
        return new MenuProduct(product, quantity);
    }
}
