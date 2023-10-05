package swu.musling.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.config.s3.config.S3Uploader;
import swu.musling.config.security.JwtTokenProvider;
import swu.musling.member.Role;
import swu.musling.member.dto.LoginRequestDto;
import swu.musling.member.dto.MemberRequestDto;
import swu.musling.member.jpa.Member;
import swu.musling.member.jpa.MemberRepository;
import swu.musling.member.jpa.Profile;
import swu.musling.member.jpa.ProfileRepository;

import java.io.IOException;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private S3Uploader s3Uploader;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, ProfileRepository profileRepository,
                             JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder,
                             S3Uploader s3Uploader) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.s3Uploader = s3Uploader;
    }

    @Transactional
    @Override
    public UUID createMember(MemberRequestDto memberRequestDto, MultipartFile file) { //회원가입

        Member member = Member.builder()
                .email(memberRequestDto.getEmail())
                .pwd(passwordEncoder.encode(memberRequestDto.getPwd()))
                .name(memberRequestDto.getName())
                .age(memberRequestDto.getAge())
                .role(Role.ROLE_MEMBER)
                .build();

        member = memberRepository.save(member);


        if (!file.isEmpty()) {
            try {
                String storedFileName = s3Uploader.upload(file, "images");

                Profile profile = Profile.builder()
                        .member(member)
                        .imageUrl(storedFileName)
                        .build();

                profileRepository.save(profile);

            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        return member.getMemberId();
    }

    @Override
    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginRequestDto) {  //로그인
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 아이디입니다."));

        if (!passwordEncoder.matches(loginRequestDto.getPwd(), member.getPwd())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 맞지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRole());
        return token;
    }

    @Override
    @Transactional
    public String out(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));

       memberRepository.delete(member);

        return member.getEmail();
    }
}
