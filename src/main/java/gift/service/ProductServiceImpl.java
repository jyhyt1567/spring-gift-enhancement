package gift.service;

import gift.dto.CreateProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

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
    public List<ProductResponseDto> findAllProducts() {

        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productsList = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto responseDto = new ProductResponseDto(product.getId(),
                    product.getName(), product.getPrice(), product.getImageUrl());
            productsList.add(responseDto);
        }
        return productsList;
    }

    @Override
    public ProductResponseDto findProductById(Long id) {
        Product find = findProductByIdOrElseThrow(id);

        return new ProductResponseDto(find.getId(), find.getName(),
                find.getPrice(), find.getImageUrl());
    }

    @Override
    public ProductResponseDto updateProductById(Long id, CreateProductRequestDto requestDto) {
        findProductByIdOrElseThrow(id);
        Product newProduct = new Product(id, requestDto.name(), requestDto.price(),
                requestDto.imageUrl());
        productRepository.save(newProduct);
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
}
