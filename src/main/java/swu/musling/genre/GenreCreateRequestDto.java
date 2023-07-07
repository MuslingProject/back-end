package swu.musling.genre;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swu.musling.membership.Member;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class GenreCreateRequestDto {
    private String userId;
    private int indie;
    private int balad;
    private int rockMetal;
    private int dancePop;
    private int rapHiphop;
    private int rbSoul;
    private int forkAcoustic;

    @Builder
    public GenreCreateRequestDto(String userId, int indie, int balad, int rockMetal,
                                 int dancePop, int rapHiphop, int rbSoul, int forkAcoustic) {
        this.userId = userId;
        this.indie = indie;
        this.balad = balad;
        this.rockMetal = rockMetal;
        this.dancePop = dancePop;
        this.rapHiphop = rapHiphop;
        this.rbSoul = rbSoul;
        this.forkAcoustic = forkAcoustic;
    }
}
