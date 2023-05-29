package swu.musling.membership;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swu.musling.diary.DiaryVo;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //회원가입 api
    @PostMapping("/users/new-user")
    void joinMember(@RequestBody MemberVo memberVo) {    //JSON 형식으로 요청
        try {
            memberService.join(memberVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
