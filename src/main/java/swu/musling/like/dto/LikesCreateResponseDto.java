package swu.musling.like.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LikesCreateResponseDto {
    private Integer likeIds;
}

