package swu.musling.genre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swu.musling.genre.dto.GenreRequestDto;
import swu.musling.genre.dto.GenreResponseDto;
import swu.musling.genre.jpa.Genre;
import swu.musling.genre.jpa.GenreRepository;
import swu.musling.member.jpa.Member;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public GenreResponseDto getGenre(Member member) {   //장르 조회
        Genre genre = genreRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("선호 장르 정보가 없습니다."));

        return GenreResponseDto.builder()
                .indie(genre.isIndie())
                .balad(genre.isBalad())
                .rockMetal(genre.isRockMetal())
                .dancePop(genre.isDancePop())
                .rapHiphop(genre.isRapHiphop())
                .rbSoul(genre.isRbSoul())
                .forkAcoustic(genre.isForkAcoustic())
                .build();
    }

    @Override
    @Transactional
    public GenreResponseDto saveGenre(Member member, GenreRequestDto genreRequestDto) { //장르 저장
        Genre genre = Genre.builder()
                .member(member)
                .indie(genreRequestDto.isIndie())
                .balad(genreRequestDto.isBalad())
                .rockMetal(genreRequestDto.isRockMetal())
                .dancePop(genreRequestDto.isDancePop())
                .rapHiphop(genreRequestDto.isRapHiphop())
                .rbSoul(genreRequestDto.isRbSoul())
                .forkAcoustic(genreRequestDto.isForkAcoustic())
                .build();

        genreRepository.save(genre);

        return GenreResponseDto.builder()
                .indie(genre.isIndie())
                .balad(genre.isBalad())
                .rockMetal(genre.isRockMetal())
                .dancePop(genre.isDancePop())
                .rapHiphop(genre.isRapHiphop())
                .rbSoul(genre.isRbSoul())
                .forkAcoustic(genre.isForkAcoustic())
                .build();
    }

    @Override
    @Transactional
    public GenreResponseDto updateGenre(Member member, GenreRequestDto genreRequestDto) {
        Genre genre = genreRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("선호 장르 정보가 없습니다."));

        //update
        genre.update(genreRequestDto);  // update 메서드를 사용하여 Genre 엔터티 업데이트
        genreRepository.save(genre);

        return GenreResponseDto.builder()
                .indie(genre.isIndie())
                .balad(genre.isBalad())
                .rockMetal(genre.isRockMetal())
                .dancePop(genre.isDancePop())
                .rapHiphop(genre.isRapHiphop())
                .rbSoul(genre.isRbSoul())
                .forkAcoustic(genre.isForkAcoustic())
                .build();
    }
}
