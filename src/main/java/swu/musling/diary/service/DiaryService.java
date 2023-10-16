package swu.musling.diary.service;

import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.DiaryResponseDto;
import swu.musling.diary.jpa.Diary;
import swu.musling.member.jpa.Member;

public interface DiaryService {
    DiaryResponseDto createDiary(Member member, CreateDiaryRequestDto requestDto); //일기 등록
}

