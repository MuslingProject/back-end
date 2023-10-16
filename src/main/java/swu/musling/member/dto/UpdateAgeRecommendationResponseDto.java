package swu.musling.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAgeRecommendationResponseDto {
    private boolean oldAgeRecommendation;   //기존 장르 추천 on/off
    private boolean newAgeRecommendation;   //새로운 장르 추천 on/off
}
