package swu.musling.music.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {
    // 장르 문자열을 포함하고 특정 카테고리에 속하는 Music 목록을 조회하는 메소드
    List<Music> findByGenresContainingAndCategory(String genre, Category category);

    // 년도 범위를 추가한 음악 검색 메소드
    List<Music> findByGenresContainingAndYearsBetweenAndCategory(String genre, int yearRangeStart, int currentYear, Category category);
}
