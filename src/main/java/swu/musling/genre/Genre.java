package swu.musling.genre;

import lombok.*;
import swu.musling.membership.Member;
import swu.musling.membership.PatchGenreRequestDto;
import swu.musling.membership.Profile;

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
    private int rockMetal;
    private int dancePop;
    private int rapHiphop;
    private int rbSoul;
    private int forkAcoustic;

    public Genre update(PatchGenreRequestDto patchGenreRequestDto) {
        this.indie = indie;
        this.balad = balad;
        this.rockMetal = rockMetal;
        this.dancePop = dancePop;
        this.rapHiphop = rapHiphop;
        this.rbSoul = rbSoul;
        this.forkAcoustic = forkAcoustic;

        return this;
    }
}
