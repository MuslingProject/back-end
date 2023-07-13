package swu.musling.genre;

import lombok.*;
import swu.musling.membership.Member;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class GenreCreateRequestDto {
    private Member member;
    private int indie;
    private int balad;
    private int rockMetal;
    private int dancePop;
    private int rapHiphop;
    private int rbSoul;
    private int forkAcoustic;

    @Builder
    public GenreCreateRequestDto(Member member, int indie, int balad, int rockMetal,
                                 int dancePop, int rapHiphop, int rbSoul, int forkAcoustic) {
        this.member = member;
        this.indie = indie;
        this.balad = balad;
        this.rockMetal = rockMetal;
        this.dancePop = dancePop;
        this.rapHiphop = rapHiphop;
        this.rbSoul = rbSoul;
        this.forkAcoustic = forkAcoustic;
    }

    public Genre toEntity() {
        return Genre.builder()
                .member(member)
                .indie(indie)
                .balad(balad)
                .rockMetal(rockMetal)
                .dancePop(dancePop)
                .rapHiphop(rapHiphop)
                .rbSoul(rbSoul)
                .forkAcoustic(forkAcoustic)
                .build();
    }
}
