package swu.musling.membership;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입 로직
    //1. 구글
    //2. 자체
    public void join(MemberVo memberVo) throws Exception{
        Member member = new Member();

        //구글로 회원가입
        if (memberVo.getId().contains("@gmail.com")) {
            member.setId(memberVo.getId());
            member.setName(memberVo.getName());
            memberRepository.save(member);
        } else {
            member.setId(memberVo.getId());
            member.setPwd(memberVo.getPwd());
            member.setName(memberVo.getName());
            memberRepository.save(member);
        }
    }
}
