package swu.musling.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor // 전체 필드 생성자 추가
public class LikesCreateRequestDto {
    private List<LikeData> likes;

    @Data
    @Builder
    @NoArgsConstructor  // 기본 생성자 추가
    @AllArgsConstructor // 전체 필드 생성자 추가
    public static class LikeData {
        private String titles;
        private String imgs;
        private String singers;
        private String emotion;
        private String weather;
    }
}

