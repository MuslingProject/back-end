package swu.musling.diary;

import java.time.LocalDate;

public interface DiaryService {
    void createDiary(DiaryVo diaryVo);
    void deleteDiary(DeleteRequestDto deleteRequestDto);
}
