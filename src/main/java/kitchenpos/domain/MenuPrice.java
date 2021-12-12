package kitchenpos.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {

    private static final int MIN_PRICE = 0;

    @Column
    private BigDecimal price;

    public MenuPrice(BigDecimal price) {
        check(price);
        this.price = price;
    }

    protected MenuPrice() {

    }

    private void check(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < MIN_PRICE) {
            throw new IllegalArgumentException();
        }
    }

    public void checkLessThan(BigDecimal price) {
        if (this.price.compareTo(price) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal getPrice() {
        return price;
    }
}