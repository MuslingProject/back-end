package swu.musling.membership;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import swu.musling.securityspring.config.JwtTokenProvider;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    //회원가입
    @ResponseBody
    @PostMapping("/new-user")
    public UUID register(@RequestBody Map<String, String> user) {
        if (user.get("user_id").contains("@gmail.com")) {   //구글 회원가입의 경우
            return memberRepository.save(Member.builder()
                    .userid(user.get("user_id"))
                    .name(user.get("name"))
                    .age(Integer.parseInt(user.get("age")))
                    .role(Role.ROLE_MEMBER)
                    .build()).getId();
        } else {    //자체 회원가입의 경우
            return memberRepository.save(Member.builder()
                    .userid(user.get("user_id"))
                    .pwd(passwordEncoder.encode(user.get("pwd")))
                    .name(user.get("name"))
                    .age(Integer.parseInt(user.get("age")))
                    .role(Role.ROLE_MEMBER)
                    .build()).getId();
        }
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {

        Member member = memberRepository.findByUserid(user.get("user_id"))
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 아이디입니다."));

        if (!passwordEncoder.matches(user.get("pwd"), member.getPwd())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 맞지 않습니다.");
        }

        return jwtTokenProvider.createToken(member.getUserid(), member.getRole());
    }
}
