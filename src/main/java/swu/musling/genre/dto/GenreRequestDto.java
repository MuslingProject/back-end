package swu.musling.genre.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreRequestDto {
    private boolean indie;
    private boolean balad;
    private boolean rockMetal;
    private boolean dancePop;
    private boolean rapHiphop;
    private boolean rbSoul;
    private boolean forkAcoustic;
}
