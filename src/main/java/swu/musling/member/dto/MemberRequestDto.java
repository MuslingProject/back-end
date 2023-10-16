package swu.musling.member.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class MemberRequestDto {
    private String email;  //사용자 지정 id
    private String pwd;
    private String name;
    private String age;
    private boolean ageRecommendation;
    private MultipartFile file;
}
