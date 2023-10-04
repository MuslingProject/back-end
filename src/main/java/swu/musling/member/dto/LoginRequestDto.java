package swu.musling.member.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String id;  //사용자 지정 id
    private String pwd;
}
