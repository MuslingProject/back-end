package swu.musling.diary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import swu.musling.diary.dto.*;
import swu.musling.member.jpa.Member;

import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    CreateDiaryResponseDto createDiary(Member member, CreateDiaryRequestDto requestDto); //일기 등록
    void deleteDiary(Member member, Long diaryId); //일기 삭제
    DiaryResponseDto getDiary(Member member, Long diaryId);    //일기 개별 조회
    List<DiaryResponseDto> getDiariesByDate(Member member, LocalDate date); //특정 날짜 일기 전체 조회
    Page<DiaryResponseDto> getAllDiaries(Member member, Pageable pageable); //일기 전체 조회
    EmotionCountResponseDto getEmotionCounts(Member member);    //감정 개수 조회
    List<RecommendationDto> reRecommendSongs(Long diaryId, Member member); //노래 재추천
}

