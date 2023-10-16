package swu.musling.diary.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swu.musling.music.jpa.Music;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recommendation")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long recommendationId;

    @Column(name = "song_title", nullable = false)
    private String songTitle;

    @Column(name = "cover_image_path", nullable = false)
    private String coverImagePath;

    @Column(name = "singer", nullable = false)
    private String singer;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "weather")
    private String weather;

    @ManyToOne
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Builder
    public Recommendation(String songTitle, String coverImagePath, String singer, String emotion, String weather,
                          Diary diary) {
        this.songTitle = songTitle;
        this.coverImagePath = coverImagePath;
        this.singer = singer;
        this.emotion = emotion;
        this.weather = weather;
        this.diary = diary;
    }
}

