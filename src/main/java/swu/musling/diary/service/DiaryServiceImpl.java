package swu.musling.diary.service;

import org.springframework.stereotype.Service;
import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.CreateDiaryResponseDto;
import swu.musling.member.jpa.Member;

@Service
public class DiaryServiceImpl implements DiaryService {
    @Override
    public CreateDiaryResponseDto createDiary(Member member, CreateDiaryRequestDto requestDto) {
        return null;
    }
}
