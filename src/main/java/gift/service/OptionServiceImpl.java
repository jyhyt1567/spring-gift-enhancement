package gift.service;

import gift.dto.CreateOptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.repository.OptionRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl implements OptionService{

    private final OptionRepository optionRepository;

    private final ProductService productService;

    public OptionServiceImpl(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    @Override
    public List<OptionResponseDto> findProductOptionById(Long id) {
        List<Option> options = findOptionsByProductIdOrElseThrow(id);
        return toOptionResponseDtoList(options);
    }

    @Override
    public OptionResponseDto createOption(CreateOptionRequestDto requestDto, Long productId) {
        checkDuplicateOption(productId, requestDto.name());
        Option newOption = new Option(requestDto.name(), requestDto.quantity(), null);
        Product product = productService.findProductByIdOrElseThrow(productId);
        newOption.setProduct(product);
        Option savedOption = optionRepository.save(newOption);
        return new OptionResponseDto(savedOption.getName(), savedOption.getQuantity());
    }

    private void checkDuplicateOption(Long productId, String name) {
        optionRepository.findByProduct_IdAndName(productId, name)
                .ifPresent(option -> {
                    throw new CustomException(ErrorCode.AlreadyExistOptionName);
                });
    }

    private List<OptionResponseDto> toOptionResponseDtoList (List<Option> options) {
        List<OptionResponseDto> optionResponseDtos = new ArrayList<>();
        for(Option option : options){
            optionResponseDtos.add(new OptionResponseDto(option.getName(), option.getQuantity()));
        }
        return optionResponseDtos;
    }

    private List<Option> findOptionsByProductIdOrElseThrow(Long productId) {
        List<Option> options =  optionRepository.findByProduct_Id(productId);
        if (options.isEmpty()){
            throw new CustomException(ErrorCode.ProductNotfound);
        }
        return options;
    }
}
