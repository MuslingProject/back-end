package swu.musling.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.ApiResponse;
import swu.musling.config.security.SecurityUser;
import swu.musling.member.dto.*;
import swu.musling.member.jpa.Member;
import swu.musling.member.service.MemberService;

import java.io.IOException;

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
            return ApiResponse.createError(HttpStatus.INTERNAL_SERVER_ERROR, "사진 업로드 실패");
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

    //회원 조회
    @GetMapping
    public ApiResponse<MemberResponseDto> getMyInfo(@AuthenticationPrincipal SecurityUser principal) {
        return ApiResponse.createSuccess(memberService.getMemberInfo(principal.getMember()));
    }

    //프로필 사진 수정
    @PatchMapping("/profile")
    public ApiResponse<?> updateProfile(@AuthenticationPrincipal SecurityUser principal,
                                        @RequestParam("file") MultipartFile file) {
        try {
            memberService.updateProfile(principal.getMember(), file);
            return ApiResponse.createSuccessWithNoData("프로필 사진 수정 성공");
        }  catch (RuntimeException e) {
            return ApiResponse.createError(HttpStatus.INTERNAL_SERVER_ERROR, "사진 업로드 실패");
        }
    }

    //이름 수정
    @PatchMapping("/name")
    public ApiResponse<UpdateNameResponseDto> updateName(@AuthenticationPrincipal SecurityUser principal,
                                                         @RequestBody UpdateNameRequestDto requestDto) {
        return ApiResponse.createSuccess(memberService.updateName(principal.getMember(), requestDto));
    }

    //연령대 추천 on/off 수정
    @PatchMapping("/ageRecommendation")
    public ApiResponse<UpdateAgeRecommendationResponseDto> updateName(@AuthenticationPrincipal SecurityUser principal,
                                                         @RequestBody UpdateAgeRecommendationRequestDto requestDto) {
        return ApiResponse.createSuccess(memberService.updateAgeRecommendation(principal.getMember(), requestDto));
    }
}
