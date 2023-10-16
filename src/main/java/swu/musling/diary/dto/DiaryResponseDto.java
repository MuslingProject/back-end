package swu.musling.diary.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DiaryResponseDto {
    private String title;
    private LocalDate date;
    private String weather;
    private String content;
    private String mood;
}
