package swu.musling.like.dto;

import lombok.Builder;
import lombok.Data;
import swu.musling.like.jpa.Likes;

@Data
@Builder
public class LikesResponseDto {
    private Integer likesId;
    private String titles;
    private String imgs;
    private String singers;
    private String emotion;
    private String weather;

    public static LikesResponseDto fromEntity(Likes likes) {
        return LikesResponseDto.builder()
                .likesId(likes.getLikesId())
                .titles(likes.getTitles())
                .imgs(likes.getImgs())
                .singers(likes.getSingers())
                .emotion(likes.getEmotion())
                .weather(likes.getWeather())
                .build();
    }
}

