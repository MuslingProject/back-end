package swu.musling.member.dto;

import lombok.Data;

@Data
public class UpdateAgeRecommendationRequestDto {
    //장르 추천 on/off 시 사용하는 dto
    private boolean ageRecommendation;
}
