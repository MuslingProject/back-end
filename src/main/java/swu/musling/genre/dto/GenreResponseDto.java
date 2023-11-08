package swu.musling.genre.dto;

import lombok.Builder;
import lombok.Data;
import swu.musling.genre.jpa.Genre;

@Data
@Builder
public class GenreResponseDto {
    private boolean indie;
    private boolean balad;
    private boolean rockMetal;
    private boolean dancePop;
    private boolean rapHiphop;
    private boolean rbSoul;
    private boolean forkAcoustic;

    //저장한 결과를 dto로 반환하기 위한 Entity to DTO
    public static GenreResponseDto of(Genre genre) {
        return GenreResponseDto.builder()
                .indie(genre.isIndie())
                .balad(genre.isBalad())
                .rockMetal(genre.isRockMetal())
                .dancePop(genre.isDancePop())
                .rapHiphop(genre.isRapHiphop())
                .rbSoul(genre.isRbSoul())
                .forkAcoustic(genre.isForkAcoustic())
                .build();
    }
}
