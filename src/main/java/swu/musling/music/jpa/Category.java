package swu.musling.music.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import swu.musling.diary.jpa.Recommendation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name", nullable = false)
    private String name;

    //1:n 관계 설정
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Music> musics = new ArrayList<>();
}
