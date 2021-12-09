package kitchenpos.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kitchenpos.dao.MenuGroupDao;
import kitchenpos.domain.MenuGroup;

@ExtendWith(MockitoExtension.class)
public class MenuGroupServiceTest {
    @Mock
    private MenuGroupDao menuGroupDao;

    @InjectMocks
    private MenuGroupService menuGroupService;

    private MenuGroup 치킨_메뉴그룹 = new MenuGroup();
    private MenuGroup 사이드_메뉴그룹 = new MenuGroup();
    
    @BeforeEach
    void setUp() {
        치킨_메뉴그룹.setId(1L);
        치킨_메뉴그룹.setName("치킨");

        사이드_메뉴그룹.setId(2L);
        사이드_메뉴그룹.setName("사이드");
    }
    
    @DisplayName("메뉴그룹이 저장된다.")
    @Test
    void create_menuGroup() {
        // given
        when(menuGroupDao.save(any(MenuGroup.class))).thenReturn(this.치킨_메뉴그룹);

        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setName("치킨");

        // when
        MenuGroup savedMenuGroup = menuGroupService.create(menuGroup);

        // then
        Assertions.assertThat(savedMenuGroup).isEqualTo(this.치킨_메뉴그룹);
    }

    @DisplayName("메뉴그룹들이 조회된다.")
    @Test
    void search_menuGroup() {
        // given
        when(menuGroupDao.findAll()).thenReturn(List.of(this.치킨_메뉴그룹, this.사이드_메뉴그룹));

        // when
        List<MenuGroup> searchedMenuGroups = menuGroupService.list();

        // then
        Assertions.assertThat(searchedMenuGroups).isEqualTo(List.of(this.치킨_메뉴그룹, this.사이드_메뉴그룹));
    }
}
