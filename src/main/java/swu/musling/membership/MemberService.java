package swu.musling.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.config.s3.config.S3Uploader;

import java.io.IOException;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    private S3Uploader s3Uploader;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public UUID signUp(MultipartFile image, Member member) throws IOException {
        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            member.setImageUrl(storedFileName);
        }

        if (member.getUserid().contains("@gmail.com")) {   //구글 회원가입의 경우
            return memberRepository.save(Member.builder()
                    .userid(member.getUserid())
                    .name(member.getName())
                    .age(member.getAge())
                    .role(Role.ROLE_MEMBER)
                    .imageUrl(member.getImageUrl())
                    .build()).getId();
        } else {    //자체 회원가입의 경우
            return memberRepository.save(Member.builder()
                    .userid(member.getUserid())
                    .pwd(member.getPwd())
                    .name(member.getName())
                    .age(member.getAge())
                    .role(Role.ROLE_MEMBER)
                    .imageUrl(member.getImageUrl())
                    .build()).getId();
        }
    }
}
