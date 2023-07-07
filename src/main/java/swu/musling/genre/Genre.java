package swu.musling.genre;

import lombok.*;
import swu.musling.membership.Member;

import javax.persistence.*;
import java.util.Optional;

@Entity(name = "genre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreid;
    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
    private int indie;
    private int balad;
    private int rock_metal;
    private int dance_pop;
    private int rap_hiphop;
    private int rb_soul;
    private int fork_acoustic;

    @Builder
    public Genre(Member member, int indie, int balad, int rockMetal,
                 int dancePop, int rapHiphop, int rbSoul, int forkAcoustic) {
        this.member = member;
        this.indie = indie;
        this.balad = balad;
        this.rock_metal = rockMetal;
        this.dance_pop = dancePop;
        this.rap_hiphop = rapHiphop;
        this.rb_soul = rbSoul;
        this.fork_acoustic = forkAcoustic;
    }
}
