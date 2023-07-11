package swu.musling.diary;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import swu.musling.membership.Member;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private int diaryId;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;
    private String title;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private String weather;
    private String moodResult; //모델을 돌리고 나온 분위기 결과값
}
