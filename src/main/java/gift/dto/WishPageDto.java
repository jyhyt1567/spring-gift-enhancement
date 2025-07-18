package gift.dto;

import gift.entity.Wish;
import java.util.List;

public record WishPageDto(
        List<WishResponseDto> content,
        int pageNum,
        int totalPageNum
) {

}
