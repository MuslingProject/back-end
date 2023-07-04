package swu.musling.diary;

import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    void createDiary(DiaryVo diaryVo);  //일기 생성
    void deleteDiary(DeleteRequestDto deleteRequestDto);   //일기 삭제
    List<Diary> readDiary(DiaryReadRequestDto diaryReadRequestDto); //일기 개별 조회
    List<Diary> readMonthlyDiary(DiaryMonthlyReadRequestDto diaryMonthlyReadRequestDto);    //월별 일기 조회
}
