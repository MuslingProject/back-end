package swu.musling.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatchGenreRequestDto {
    private int indie;
    private int balad;
    private int rockMetal;
    private int dancePop;
    private int rapHiphop;
    private int rbSoul;
    private int forkAcoustic;
}
