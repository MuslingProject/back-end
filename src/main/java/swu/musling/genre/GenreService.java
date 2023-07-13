package swu.musling.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swu.musling.ResponseDto;
import swu.musling.diary.Diary;
import swu.musling.membership.Member;
import swu.musling.membership.MemberRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseDto create(GenreCreateRequestDto requestDto) throws Exception{
        try {
            genreRepository.save(requestDto.toEntity());
            return new ResponseDto(200, "장르저장 성공");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
