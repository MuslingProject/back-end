package swu.musling.membership;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.ResponseDto;
import swu.musling.config.securityspring.config.SecurityUser;
import swu.musling.genre.GenreCreateRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final MyPageService myPageService;

    //회원정보 조회
    @GetMapping
    public ResponseEntity create(@AuthenticationPrincipal SecurityUser principal) throws Exception{
        try {
            // 프론트에서 받은 토큰으로 Member 구분함
            //requestDto.setMember(principal.getMember());
            MyPageResponseDto responseDto = myPageService.getMyPage(principal.getMember());
            return ResponseEntity.ok().body(ResponseDto.response(200, "회원 정보 가져오기 성공", responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(400, e.getMessage()));
        }
    }

    //회원 프로필 사진 수정
    @PatchMapping("/image")
    public ResponseEntity patchUserImage(
            @AuthenticationPrincipal SecurityUser principal,
            @RequestParam(value="image") MultipartFile newImage) {
        try {
            ResponseDto responseDto = myPageService.updateImage(principal.getMember(), newImage);
            return ResponseEntity.ok().body(ResponseDto.response(200, responseDto.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(400, e.getMessage()));
        }
    }
    //회원 별명 수정
    @PatchMapping("/nickname")
    public ResponseEntity patchUserName(@AuthenticationPrincipal SecurityUser principal, @RequestBody String newName) {
        try {
            ResponseDto responseDto = myPageService.updateName(principal.getMember(), newName);
            return ResponseEntity.ok().body(ResponseDto.response(200, responseDto.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(400, e.getMessage()));
        }

    }

    //회원 선호 장르 수정
    @PatchMapping("/genre")
    public ResponseEntity patchUserGenre(@AuthenticationPrincipal SecurityUser principal, @RequestBody PatchGenreRequestDto patchGenreRequestDto) {
        try {
            ResponseDto responseDto = myPageService.updateGenre(principal.getMember(), patchGenreRequestDto);
            return ResponseEntity.ok().body(ResponseDto.response(200, responseDto.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(400, e.getMessage()));
        }
    }
}
