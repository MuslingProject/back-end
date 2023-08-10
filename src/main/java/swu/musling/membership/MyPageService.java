package swu.musling.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swu.musling.ResponseDto;
import swu.musling.config.s3.config.S3Uploader;
import swu.musling.config.s3.service.S3UploadService;
import swu.musling.genre.Genre;
import swu.musling.genre.GenreRepository;


import java.util.Optional;

@Service
public class MyPageService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final GenreRepository genreRepository;
//    private final S3UploadService s3UploadService;
    private final S3Uploader s3Uploader;

    @Autowired
    public MyPageService(MemberRepository memberRepository, ProfileRepository profileRepository, GenreRepository genreRepository, S3UploadService s3UploadService, S3Uploader s3Uploader) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
        this.genreRepository = genreRepository;
//        this.s3UploadService = s3UploadService;
        this.s3Uploader = s3Uploader;
    }

    public MyPageResponseDto getMyPage(Member member) throws Exception {
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

    @Transactional
    public ResponseDto updateImage(Member member, MultipartFile newImage) throws Exception {
        try {
            if(newImage.isEmpty()) {
                throw new Exception("필수 데이터 누락");
            } else {
                Optional<Member> user = memberRepository.findByUserId(member.getUserId());

                //기존 파일 s3 경로
                String originalFileName = user.get().getProfile().getImageUrl();
                //기존 파일 s3에서 삭제
//                s3Uploader.delete(originalFileName.split("/")[4]);
                s3Uploader.delete(originalFileName);
                //db의 s3 경로 바꿔주고 s3에 재업로드
                String storedFileName = s3Uploader.upload(newImage,"images");
                //db 수정
                profileRepository.save(user.get().getProfile().update(storedFileName));

                return new ResponseDto(200, "회원 사진 수정 성공");

            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public ResponseDto updateName(Member member, String newName) throws Exception {
        try {
            if(newName == null) {
                throw new Exception("필수 데이터 누락");
            }
            else {
                Optional<Member> user = memberRepository.findByUserId(member.getUserId());

                if(user.get().getName().equals(newName)) {
                    throw new Exception("잘못된 요청");
                }

                memberRepository.save(user.get().update(newName));
                return new ResponseDto(200, "회원 이름 수정 성공");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public ResponseDto updateGenre(Member member, PatchGenreRequestDto patchGenreRequestDto) throws Exception {
        try {
            if (patchGenreRequestDto == null) {
                throw new IllegalArgumentException("필수 데이터 누락");
            }
            else {
                Optional<Member> user = memberRepository.findByUserId(member.getUserId());
                Genre genre = genreRepository.findByMember_Id(user.get().getId());

                if (user.get().getGenres().equals(patchGenreRequestDto)) {
                    throw new Exception("잘못된 요청");
                }
                genreRepository.save(genre.update(patchGenreRequestDto));
                return new ResponseDto(200, "장르 수정 성공");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
