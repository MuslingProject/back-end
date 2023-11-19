package swu.musling.diary.dto;

import lombok.Builder;
import lombok.Data;
import swu.musling.diary.jpa.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DiaryResponseDto {
    private Long diaryId;
    private String title;
    private LocalDate date;
    private String weather;
    private String content;
    private String mood;
    private List<RecommendationDto> recommendations;

    public static DiaryResponseDto fromEntity(Diary diary) {
        List<RecommendationDto> recommendationDtos = diary.getRecommendations().stream()
                .map(RecommendationDto::fromEntity)
                .collect(Collectors.toList());

        return DiaryResponseDto.builder()
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

