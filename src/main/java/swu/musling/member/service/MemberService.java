package swu.musling.member.service;

import org.springframework.web.multipart.MultipartFile;
import swu.musling.member.dto.LoginRequestDto;
import swu.musling.member.dto.MemberRequestDto;
import swu.musling.member.dto.UpdateNameRequestDto;
import swu.musling.member.dto.UpdateNameResponseDto;
import swu.musling.member.jpa.Member;

import java.io.IOException;
import java.util.UUID;

public interface MemberService {
    UUID createMember(MemberRequestDto memberDto, MultipartFile file) throws IOException;    //회원가입
    String login(LoginRequestDto loginRequestDto);  //로그인
    String out(String email); // 회원 탈퇴
    void updateProfile(Member member, MultipartFile file);  //프로필 사진 수정
     UpdateNameResponseDto updateName(Member member, UpdateNameRequestDto requestDto);
}
