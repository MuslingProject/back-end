package swu.musling.diary.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateDiaryRequestDto {
    private String title;
    private LocalDate date;
    private String weather;
    private String content;
    // mood 필드는 인공지능 API를 통해 받아올 것이므로 여기에는 포함하지 않음.
}
