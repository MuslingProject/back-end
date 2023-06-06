package swu.musling.genre;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swu.musling.membership.Member;

@Getter
@NoArgsConstructor
public class GenreCreateRequestDto {
    private String memberId;
    private boolean indie;
    private boolean balad;
    private boolean rock_metal;
    private boolean dance_pop;
    private boolean rap_hiphop;
    private boolean rb_soul;
    private boolean fork_acoustic;

    @Builder
    public GenreCreateRequestDto(String memberId, boolean indie, boolean balad, boolean rockMetal,
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

    public Genre toEntity() {
        return Genre.builder()
                .memberId(memberId)
                .indie(indie)
                .balad(balad)
                .rockMetal(rock_metal)
                .dancePop(dance_pop)
                .rapHiphop(rap_hiphop)
                .rbSoul(rb_soul)
                .forkAcoustic(fork_acoustic)
                .build();
    }
}
