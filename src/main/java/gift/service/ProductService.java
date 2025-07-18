package gift.service;

import gift.dto.CreateProductRequestDto;
import gift.dto.ProductPageDto;
import gift.dto.ProductResponseDto;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponseDto createProduct(CreateProductRequestDto requestDto);

    ProductPageDto findAllProducts(Pageable pageable);

    ProductResponseDto findProductById(Long id);

    ProductResponseDto updateProductById(Long id, CreateProductRequestDto requestDto);

    void deleteProductById(Long id);
}
