package swu.musling.diary.dto;

import lombok.Builder;
import lombok.Data;
import swu.musling.diary.jpa.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CreateDiaryResponseDto {
    private Long diaryId;
    private String title;
    private LocalDate date;
    private String weather;
    private String content;
    private String mood;
    private List<RecommendationDto> recommendations;


    // Entity를 Dto로 변환하는 로직을 DTO클래스 내부에 두는 방식
    public static CreateDiaryResponseDto fromEntity(Diary diary) {
        //Recommendations 객체를 RecommendationDto의 fromEntity 메서드를 참조해 RecommendationDto 객체로 변환하고 list로 변환하여 반환
        List<RecommendationDto> recommendationDtos = diary.getRecommendations().stream()
                .map(RecommendationDto::fromEntity)
                .collect(Collectors.toList());

        return CreateDiaryResponseDto.builder()
                .diaryId(diary.getDiaryId())
                .title(diary.getTitle())
                .date(diary.getDate())
                .weather(diary.getWeather())
                .content(diary.getContent())
                .mood(diary.getMood())
                .recommendations(recommendationDtos)
                .build();
    }
}
