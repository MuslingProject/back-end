package swu.musling.diary;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService{
    private final DiaryRepository diaryRepository;

    public DiaryServiceImpl(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
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

    @Override
    public void deleteDiary(DeleteRequestDto deleteRequestDto) {
        diaryRepository.deleteAllByUserIdAndDate(deleteRequestDto.getUserId(), deleteRequestDto.getDate());
    }
    @Override
    public List<Diary> readDiary(DiaryReadRequestDto diaryReadRequestDto) {
        return  diaryRepository.findAllByUserIdAndDate(diaryReadRequestDto.getUserId(), diaryReadRequestDto.getDate());
    }

    @Override
    public List<Diary> readMonthlyDiary(DiaryMonthlyReadRequestDto diaryMonthlyReadRequestDto) {
        int month;
        month = diaryMonthlyReadRequestDto.getDate().getMonthValue();

        LocalDate startDate = diaryMonthlyReadRequestDto.getDate().withDayOfMonth(1);
        LocalDate endDate = diaryMonthlyReadRequestDto.getDate().withDayOfMonth(diaryMonthlyReadRequestDto.getDate().lengthOfMonth());

       return diaryRepository.findAllByUserIdAndDateBetween(diaryMonthlyReadRequestDto.getUserId(),startDate, endDate);
    }

}
