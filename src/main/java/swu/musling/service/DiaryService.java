package swu.musling.service;

import org.springframework.stereotype.Service;
import swu.musling.domain.Diary;
import swu.musling.domain.DiaryVo;
import swu.musling.repository.DiaryRepository;

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
        diaryRepository.save(nowDiary);
    }
}
