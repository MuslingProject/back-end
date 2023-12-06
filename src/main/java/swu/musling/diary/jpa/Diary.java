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

    @Column(name = "is_favorited")
    private Boolean isFavorited = false;   // 일기 찜하기 기능을 위한 필드, null 허용, 기본값 fasle

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 1:n 관계 설정
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations = new ArrayList<>();

    @Builder
    public Diary(Long diaryId, String title, LocalDate date, String weather, String content, String mood, Boolean isFavorited,
                 Member member) {
        this.diaryId = diaryId;
        this.title = title;
        this.date = date;
        this.weather = weather;
        this.content = content;
        this.mood = mood;
        this.isFavorited = isFavorited == null ? false : isFavorited;  // null이면 false로 설정
        this.member = member;
        this.recommendations = new ArrayList<>();
    }

    // Diary 엔티티 내에 추천을 추가하는 메서드
    public void addRecommendation(String songTitle, String coverImagePath, String singer, String emotion, String weather) {
        Recommendation recommendation = Recommendation.builder()
                .songTitle(songTitle)
                .coverImagePath(coverImagePath)
                .singer(singer)
                .emotion(emotion)
                .weather(weather)
                .diary(this) // 이렇게 해서 `Recommendation`에 `Diary`를 설정합니다.
                .build();

        this.recommendations.add(recommendation);
    }

    public void toggleFavorite() {  //여기서 toggle은 한 값에서 다른값으로 전환함을 의미
        this.isFavorited = !this.isFavorited;
    }

}
