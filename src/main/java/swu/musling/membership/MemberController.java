package swu.musling.membership;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.config.securityspring.config.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //회원가입
    @ResponseBody
    @PostMapping("/new-user")
    public UUID register(HttpServletRequest request, @RequestParam(value="image") MultipartFile image, Member member) throws IOException {
        UUID userId = memberService.signUp(image, member);
        return userId;
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {

        Member member = memberRepository.findByUserid(user.get("userid"))
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 아이디입니다."));

        if (!passwordEncoder.matches(user.get("pwd"), member.getPwd())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 맞지 않습니다.");
        }
        return jwtTokenProvider.createToken(member.getUserid(), member.getRole());
    }
}
