package swu.musling.diary.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmotionCountResponseDto {
    private Map<String, Long> emotionCounts;
    private String mostFrequentEmotion;
    private Long mostFrequentEmotionCount;
}

