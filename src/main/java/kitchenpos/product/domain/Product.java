package kitchenpos.product.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;

    public Product() {
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = validatePrice(price);
    }

    private BigDecimal validatePrice(int price) {
        BigDecimal newPrice = new BigDecimal(price);

        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격이 음수입니다. 입력한 금액 : " + price);
        }
        return newPrice;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
