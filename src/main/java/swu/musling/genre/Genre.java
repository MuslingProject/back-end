package swu.musling.genre;

import lombok.*;
import swu.musling.membership.Member;

import javax.persistence.*;

@Entity(name = "genre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue
    private Long genreid;
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private Member member;
    @Column(name = "userid")
    private String memberId;
    private boolean indie;
    private boolean balad;
    private boolean rock_metal;
    private boolean dance_pop;
    private boolean rap_hiphop;
    private boolean rb_soul;
    private boolean fork_acoustic;

    @Builder
    public Genre(String memberId, boolean indie, boolean balad, boolean rockMetal,
                 boolean dancePop, boolean rapHiphop, boolean rbSoul, boolean forkAcoustic) {
        this.memberId = memberId;
        this.indie = indie;
        this.balad = balad;
        this.rock_metal = rockMetal;
        this.dance_pop = dancePop;
        this.rap_hiphop = rapHiphop;
        this.rb_soul = rbSoul;
        this.fork_acoustic = forkAcoustic;
    }
}
