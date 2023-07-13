package swu.musling.membership;

import org.springframework.stereotype.Service;
import swu.musling.ResponseDto;

import java.util.Optional;

@Service
public class MyPageService {
    private final MemberRepository memberRepository;

    public MyPageService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MyPageResponseDto getMyPage(Member member) throws Exception{
        try {
            if (member == null) {
                throw new Exception("존재하지 않는 사용자");
            } else {
                Optional<Member> user = memberRepository.findByUserId(member.getUserId());
                return new MyPageResponseDto(user);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
