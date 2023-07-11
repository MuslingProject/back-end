package swu.musling.diary;

import java.util.List;

public interface DiaryService {
    void createDiary(DiarySaveRequestDto diarySaveRequestDto);  //일기 생성
    //void deleteDiary(DiaryDeleteRequestDto diaryDeleteRequestDto);  //일기 삭제
}
