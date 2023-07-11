package swu.musling.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void create(GenreCreateRequestDto requestDto) {
        Genre genre = new Genre();
        UUID id = memberRepository.findByUserId(requestDto.getUserId()).get().getId();
        //Member member = memberRepository.findById(id);

        //genre.setMember(member);
        genre.setIndie(requestDto.getIndie());
        genre.setBalad(requestDto.getBalad());
        genre.setRock_metal(requestDto.getRockMetal());
        genre.setDance_pop(requestDto.getDancePop());
        genre.setRap_hiphop(requestDto.getRapHiphop());
        genre.setRb_soul(requestDto.getRbSoul());
        genre.setFork_acoustic(requestDto.getForkAcoustic());

        genreRepository.save(genre);
    }
}
