package gift.service;

import gift.dto.CreateOptionRequestDto;
import gift.dto.OptionResponseDto;
import java.util.List;

public interface OptionService {

    List<OptionResponseDto> findProductOptionById(Long id);

    OptionResponseDto createOption(CreateOptionRequestDto requestDto, Long id);
}
