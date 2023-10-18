package swu.musling.diary.service;

import org.springframework.stereotype.Service;
import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.DiaryResponseDto;
import swu.musling.member.jpa.Member;

@Service
public class DiaryServiceImpl implements DiaryService {
    @Override
    public DiaryResponseDto createDiary(Member member, CreateDiaryRequestDto requestDto) {
        return null;
    }
}
