package swu.musling.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.ResponseDto;
import swu.musling.config.s3.config.S3Uploader;
import swu.musling.config.securityspring.config.JwtTokenProvider;

import java.util.Map;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private S3Uploader s3Uploader;
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, ProfileRepository profileRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public ResponseDto signUpImg(MultipartFile image) throws Exception {
        try {
            if(!image.isEmpty()) {
                String storedFileName = s3Uploader.upload(image,"images");

                Profile profile = Profile.builder()
                        .imageUrl(storedFileName)
                        .build();

                profileRepository.save(profile);

                return new ResponseDto(200, "프로필 저장 성공", profile.getProfileId());
            } else {
                throw new Exception("사진이 존재하지 않음");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public ResponseDto signUp(Map<String, String> user) throws Exception {
        try {
            if (user.get("userId").contains("@gmail.com")) {   //구글 회원가입의 경우
                Member m = Member.builder()
                        .userId(user.get("userId"))
                        .name(user.get("name"))
                        .age(user.get("age"))
                        .role(Role.ROLE_MEMBER)
                        .build();

                Profile profile = profileRepository.findById(Integer.valueOf(user.get("profileId")))
                        .orElseThrow(() -> new Exception("프로필을 찾을 수 없음"));

                m.setProfile(profile);
                memberRepository.save(m);

                return new ResponseDto(200, "구글 회원가입 성공", m.getId());

            } else {    //자체 회원가입의 경우
                Member m = Member.builder()
                        .userId(user.get("userId"))
                        .pwd(passwordEncoder.encode(user.get("pwd")))
                        .name(user.get("name"))
                        .age(user.get("age"))
                        .role(Role.ROLE_MEMBER)
                        .build();

                Profile profile = profileRepository.findById(Integer.valueOf(user.get("profileId")))
                        .orElseThrow(() -> new Exception("프로필을 찾을 수 없음"));

                m.setProfile(profile);
                memberRepository.save(m);

                return new ResponseDto(200, "자체 회원가입 성공", m.getId());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ResponseDto singIn(Map<String, String> user) throws Exception{
        try {
            Member member = memberRepository.findByUserId(user.get("userId"))
                    .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 아이디입니다."));

            if (!passwordEncoder.matches(user.get("pwd"), member.getPwd())) {
                throw new IllegalArgumentException("아이디 또는 비밀번호가 맞지 않습니다.");
            }

            String token = jwtTokenProvider.createToken(member.getUserId(), member.getRole());
            return new ResponseDto(200, "로그인 성공", token);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
