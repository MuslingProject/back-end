package swu.musling.music.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import swu.musling.diary.jpa.Diary;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "music")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Integer musicId;
    @Column(name = "titles", nullable = false)
    private String titles;
    @Column(name = "imgs", nullable = false)
    private String imgs;
    @Column(name = "singers", nullable = false)
    private String singers;
    @Column(name = "lyrics", nullable = false)
    private String lyrics;
    @Column(name = "genres", nullable = false)
    private String genres;
    @Column(name = "years", nullable = false)
    private int years;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
