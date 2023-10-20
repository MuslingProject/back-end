package swu.musling.diary.service;

import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.CreateDiaryResponseDto;
import swu.musling.member.jpa.Member;

public interface DiaryService {
    CreateDiaryResponseDto createDiary(Member member, CreateDiaryRequestDto requestDto); //일기 등록
}

