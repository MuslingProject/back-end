package swu.musling.diary.service;

import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.CreateDiaryResponseDto;
import swu.musling.diary.dto.DiaryResponseDto;
import swu.musling.member.jpa.Member;

public interface DiaryService {
    CreateDiaryResponseDto createDiary(Member member, CreateDiaryRequestDto requestDto); //일기 등록
    void deleteDiary(Long diaryId); //일기 삭제
    DiaryResponseDto getDiary(Long diaryId);    //일기 개별 조회
}

