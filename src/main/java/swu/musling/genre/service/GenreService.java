package swu.musling.genre.service;

import swu.musling.genre.dto.GenreRequestDto;
import swu.musling.genre.dto.GenreResponseDto;
import swu.musling.member.jpa.Member;

public interface GenreService {
    GenreResponseDto getGenre(Member member);   //장르 조회
    GenreResponseDto saveGenre(Member member, GenreRequestDto genreRequestDto); //장르 저장
    GenreResponseDto updateGenre(Member member, GenreRequestDto genreRequestDto);   //장르 수정
}

