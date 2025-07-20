package gift.service;

import gift.dto.CreateOptionRequestDto;
import gift.dto.CreateProductRequestDto;
import gift.dto.OptionResponseDto;
import gift.dto.ProductPageDto;
import gift.dto.ProductResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final OptionRepository optionRepository;

    public ProductServiceImpl(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto requestDto) {

        Product newProduct = new Product(requestDto.name(), requestDto.price(),
                requestDto.imageUrl(), null); //todo

        Product savedProduct = productRepository.save(newProduct);

        return new ProductResponseDto(savedProduct.getId(), savedProduct.getName(),
                savedProduct.getPrice(), savedProduct.getImageUrl());
    }

    @Override
    public ProductPageDto findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductResponseDto> responseDtos = toResponseDtoPage(products);
        return new ProductPageDto(responseDtos);
    }

    @Override
    public ProductResponseDto findProductById(Long id) {
        Product product = findProductByIdOrElseThrow(id);

        return new ProductResponseDto(product.getId(), product.getName(),
                product.getPrice(), product.getImageUrl());
    }

    @Override
    @Transactional
    public ProductResponseDto updateProductById(Long id, CreateProductRequestDto requestDto) {
        Product product = findProductByIdOrElseThrow(id);
        product.changeName(requestDto.name());
        product.changePrice(requestDto.price());
        product.changeImageUrl(requestDto.imageUrl());
        Product updated = findProductByIdOrElseThrow(id);
        return new ProductResponseDto(updated.getId(), updated.getName(), updated.getPrice(),
                updated.getImageUrl());
    }

    @Override
    public void deleteProductById(Long id) {
        findProductByIdOrElseThrow(id);
        productRepository.deleteById(id);
    }

    @Override
    public List<OptionResponseDto> findProductOptionById(Long id) {
        Product product = findProductByIdOrElseThrow(id);
        List<Option> options = product.getOptions();
        return toOptionResponseDtoList(options);
    }

    @Override
    public OptionResponseDto createOption(CreateOptionRequestDto requestDto, Long id) {
        Product product = findProductByIdOrElseThrow(id);
        checkDuplicateOption(id, requestDto.name());
        Option newOption = new Option(requestDto.name(), requestDto.quantity(), product);
        Option savedOption = optionRepository.save(newOption);
        return new OptionResponseDto(savedOption.getName(), savedOption.getQuantity());
    }

    private Product findProductByIdOrElseThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ProductNotfound));
    }

    private void checkDuplicateOption(Long productId, String name) {
        optionRepository.findByProduct_IdAndName(productId, name)
                .ifPresent(option -> {
                    throw new CustomException(ErrorCode.AlreadyExistOptionName);
                });
    }

    private Page<ProductResponseDto> toResponseDtoPage(Page<Product> page) {
        return page.map(product -> new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()));
    }

    private List<OptionResponseDto> toOptionResponseDtoList (List<Option> options) {
        List<OptionResponseDto> optionResponseDtos = new ArrayList<>();
        for(Option option : options){
            optionResponseDtos.add(new OptionResponseDto(option.getName(), option.getQuantity()));
        }
        return optionResponseDtos;
    }
}
