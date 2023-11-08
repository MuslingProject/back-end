package swu.musling.diary.dto;

import lombok.Builder;
import lombok.Data;
import swu.musling.diary.jpa.Recommendation;

@Data
@Builder
public class RecommendationDto {
    private String songTitle;
    private String coverImagePath;
    private String singer;
    private String emotion;
    private String weather;

    public static RecommendationDto fromEntity(Recommendation recommendation) {
        return RecommendationDto.builder()
                .songTitle(recommendation.getSongTitle())
                .coverImagePath(recommendation.getCoverImagePath())
                .singer(recommendation.getSinger())
                .emotion(recommendation.getEmotion())
                .weather(recommendation.getWeather())
                .build();
    }
}
