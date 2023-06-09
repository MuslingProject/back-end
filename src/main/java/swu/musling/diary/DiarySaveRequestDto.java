package swu.musling.diary;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import swu.musling.membership.Member;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiarySaveRequestDto {
    private String userId;
    private String title;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private String weather;
}
