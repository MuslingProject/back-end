package swu.musling.music.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import swu.musling.diary.jpa.Diary;
import swu.musling.diary.jpa.Recommendation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "music")
public class Music {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "titles")
    private String titles;

    @Column(name = "imgs")
    private String imgs;

    @Column(name = "singers")
    private String singers;

    @Column(name = "lyrics")
    private String lyrics;

    @Column(name = "genres")
    private String genres;

    @Column(name = "years")
    private long years;

    public Recommendation toRecommendation(Diary diary) {
        return Recommendation.builder()
                .songTitle(this.titles)
                .coverImagePath(this.imgs)
                .singer(this.singers)
                .emotion(diary.getMood())
                .diary(diary)
                .build();
    }
}

