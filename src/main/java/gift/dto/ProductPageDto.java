package gift.dto;

import java.util.List;

public record ProductPageDto(
        List<ProductResponseDto> contents,
        int pageNum,
        int totalPageNum
) {

}
