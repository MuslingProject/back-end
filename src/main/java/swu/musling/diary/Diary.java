package swu.musling.diary;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import swu.musling.membership.Member;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int diaryid;
    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
    private String title;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private String weather;
    private String musicTitle;
    private String musicSinger;
    private String musicImg;
    private String mood_result; //모델을 돌리고 나온 분위기 결과값
}
