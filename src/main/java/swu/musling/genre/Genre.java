package swu.musling.genre;

import lombok.*;
import swu.musling.membership.Member;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Integer genreId;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;
    private int indie;
    private int balad;
    private int rock_metal;
    private int dance_pop;
    private int rap_hiphop;
    private int rb_soul;
    private int fork_acoustic;
}
