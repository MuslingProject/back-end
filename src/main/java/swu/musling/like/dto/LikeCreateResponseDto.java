package swu.musling.like.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LikeCreateResponseDto {
    private Integer likeIds;
}

