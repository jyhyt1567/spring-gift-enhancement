package gift.service;

import gift.controller.ProductController;
import gift.dto.CreateProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponseDto createProduct(CreateProductRequestDto requestDto);

    Page<ProductResponseDto> findAllProducts(Pageable pageable);

    ProductResponseDto findProductById(Long id);

    ProductResponseDto updateProductById(Long id, CreateProductRequestDto requestDto);

    void deleteProductById(Long id);
}
