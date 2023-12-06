package swu.musling.like.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class LikesCreateResponseDto {
    private List<LikeInfo> likes;

    @Data
    @Builder
    public static class LikeInfo {
        private Integer likeId;
        private String titles;
        private String imgs;
        private String singers;
        private String emotion;
        private String weather;
    }
}


