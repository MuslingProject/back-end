package swu.musling.membership;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.ResponseDto;
import swu.musling.config.securityspring.config.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @ResponseBody
    @PostMapping("/new-user")
    public ResponseEntity register(@RequestBody Map<String, String> user) throws Exception {
        try {
            ResponseDto responseDto = memberService.signUp(user);
            return ResponseEntity.ok().body(ResponseDto.response(200, responseDto.getMessage(), responseDto.getData()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(400, e.getMessage()));
        }
    }

    //프로필 저장
    @ResponseBody
    @PostMapping("/new-user/profile")
    public ResponseEntity registerImg(HttpServletRequest request,
                                      @RequestParam(value="image") MultipartFile image) throws Exception {
        try {
            ResponseDto responseDto = memberService.signUpImg(image);
            return ResponseEntity.ok().body(ResponseDto.response(200, responseDto.getMessage(), responseDto.getData()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(400, e.getMessage()));
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> user) throws Exception{

        try {
            ResponseDto responseDto = memberService.singIn(user);
            return ResponseEntity.ok().body(ResponseDto.response(200, responseDto.getMessage(), responseDto.getData()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(400, e.getMessage()));
        }
    }
}
