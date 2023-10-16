package swu.musling.diary.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swu.musling.member.jpa.Member;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long diaryId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDate date;  // Java 8 이상의 java.time.LocalDate 사용

    @Column(name = "weather", nullable = false)
    private String weather;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "mood", nullable = false)
    private String mood;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 1:n 관계 설정
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations = new ArrayList<>();

    @Builder
    public Diary(String title, LocalDate date, String weather, String content, String mood, Member member) {
        this.title = title;
        this.date = date;
        this.weather = weather;
        this.content = content;
        this.mood = mood;
        this.member = member;
    }
}
