package swu.musling.diary;

import org.springframework.stereotype.Service;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(DiaryVo diaryVo) {
        //일기 값 db에 넣기
        Diary nowDiary = new Diary();
        nowDiary.setUserId(diaryVo.getUserId());
        nowDiary.setTitle(diaryVo.getTitle());
        nowDiary.setContent(diaryVo.getContent());
        nowDiary.setDate(diaryVo.getDate());
        nowDiary.setWeather(diaryVo.getWeather());
        nowDiary.setMood(diaryVo.getMood());
        nowDiary.setMusicTitle(diaryVo.getMusicTitle());
        nowDiary.setMusicSinger(diaryVo.getMusicSinger());
        nowDiary.setMusicImg(diaryVo.getMusicImg());
        nowDiary.setMood_result(diaryVo.getMood_result());
        diaryRepository.save(nowDiary);
    }
}
