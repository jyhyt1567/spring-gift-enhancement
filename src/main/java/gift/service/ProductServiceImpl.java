package gift.service;

import gift.dto.CreateProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto requestDto) {

        Product newProduct = new Product(requestDto.name(), requestDto.price(),
                requestDto.imageUrl());

        Product savedProduct = productRepository.save(newProduct);

        return new ProductResponseDto(savedProduct.getId(), savedProduct.getName(),
                savedProduct.getPrice(), savedProduct.getImageUrl());
    }

    @Override
    public Page<ProductResponseDto> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return toResponseDtoPage(products);
    }

    @Override
    public ProductResponseDto findProductById(Long id) {
        Product find = findProductByIdOrElseThrow(id);

        return new ProductResponseDto(find.getId(), find.getName(),
                find.getPrice(), find.getImageUrl());
    }

    @Override
    @Transactional
    public ProductResponseDto updateProductById(Long id, CreateProductRequestDto requestDto) {
        Product find = findProductByIdOrElseThrow(id);
        find.setName(requestDto.name());
        find.setPrice(requestDto.price());
        find.setImageUrl(requestDto.imageUrl());
        Product updated = findProductByIdOrElseThrow(id);
        return new ProductResponseDto(updated.getId(), updated.getName(), updated.getPrice(),
                updated.getImageUrl());
    }

    @Override
    public void deleteProductById(Long id) {
        findProductByIdOrElseThrow(id);
        productRepository.deleteById(id);
    }

    private Product findProductByIdOrElseThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ProductNotfound));
    }

    private Page<ProductResponseDto> toResponseDtoPage(Page<Product> page) {
        return page.map(product -> new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()));
    }
}
