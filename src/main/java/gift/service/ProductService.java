package gift.service;

import gift.dto.CreateOptionRequestDto;
import gift.dto.CreateProductRequestDto;
import gift.dto.OptionResponseDto;
import gift.dto.ProductPageDto;
import gift.dto.ProductResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponseDto createProduct(CreateProductRequestDto requestDto);

    ProductPageDto findAllProducts(Pageable pageable);

    ProductResponseDto findProductById(Long id);

    ProductResponseDto updateProductById(Long id, CreateProductRequestDto requestDto);

    void deleteProductById(Long id);

    List<OptionResponseDto> findProductOptionById(Long id);

    OptionResponseDto createOption(CreateOptionRequestDto requestDto, Long id);
}
