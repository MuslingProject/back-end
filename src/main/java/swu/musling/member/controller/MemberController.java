package swu.musling.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.ApiResponse;
import swu.musling.member.dto.LoginRequestDto;
import swu.musling.member.dto.MemberRequestDto;
import swu.musling.member.service.MemberService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    //파일과 폼 데이터를 함께 전송해야 하는 경우, @ModelAttribute를 사용하는 것이 일반적이며, 이 방식을 권장
    //multipart/form-data 형태의 파라미터를 받아 객체로 사용하고 싶을 때
    @PostMapping("/new-user")
    public ApiResponse<?> signup(@ModelAttribute MemberRequestDto memberRequestDto,
                                    @RequestParam("file") MultipartFile file) {
        try {
            return ApiResponse.createSuccess( memberService.createMember(memberRequestDto, file));
        } catch (IOException e) {
            return ApiResponse.createError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "사진 업로드 실패");
        }
    }

    // 로그인
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponse.createSuccess(memberService.login(loginRequestDto));
    }

    //회원 탈퇴
    @DeleteMapping("/{email}")
    public ApiResponse<String> out(@PathVariable String email) {
        return ApiResponse.createSuccess(memberService.out(email));
    }

}
