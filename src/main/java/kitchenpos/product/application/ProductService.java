package kitchenpos.product.application;

import kitchenpos.product.domain.ProductRepository;
import kitchenpos.product.domain.Product;
import kitchenpos.product.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(final Product product) {
        Product saved = productRepository.save(product);
        return ProductResponse.of(saved);
    }

    public List<ProductResponse> list() {
        return ProductResponse.ofList(productRepository.findAll());
    }
}