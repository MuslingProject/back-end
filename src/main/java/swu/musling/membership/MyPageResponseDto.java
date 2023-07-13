package swu.musling.membership;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class MyPageResponseDto {
    private String name;    //이름
    private Profile profile; //프로필 사진

    @Builder
    public MyPageResponseDto(Optional<Member> member) {
        this.name = member.get().getName();
        this.profile = member.get().getProfile();
    }
}
