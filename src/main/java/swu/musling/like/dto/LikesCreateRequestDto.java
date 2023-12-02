package swu.musling.like.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikesCreateRequestDto {
    private String titles;
    private String imgs;
    private String singers;
    private String emotion;
    private String weather;
}
