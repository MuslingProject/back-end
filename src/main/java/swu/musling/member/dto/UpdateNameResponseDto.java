package swu.musling.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateNameResponseDto {
    private String oldName; //기존 별명
    private String newName; //새로운 별명
}
