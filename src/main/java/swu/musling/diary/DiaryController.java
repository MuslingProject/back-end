package swu.musling.diary;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryServiceImpl diaryServiceImpl;
    MoodRequestDto moodRequestDto;
    private final MoodService moodService;

    public DiaryController(DiaryServiceImpl diaryServiceImpl, MoodService moodService) {
        this.diaryServiceImpl = diaryServiceImpl;
        this.moodService = moodService;
    }

    //Flask 서버로 일기 내용을 전달하는 api
    @PostMapping("/send_diary")
    public Mono<String> sendDiary(@RequestBody MoodRequestDto requestDto) {
        String diary = requestDto.getContent();
        // 일기 내용을 Flask 서버로 전송
        return moodService.sendDiary(diary);
    }

    //Flask 서버로부터 감정(모델 결과값) 을 가져오는 api
    @GetMapping("/get_mood")
    public Mono<MoodResponseDto> getMood() {
        // Flask 서버로부터 모델 결과값을 가져옴
        return moodService.getMood()
                .map(response -> {
                    MoodResponseDto responseDto = new MoodResponseDto();
                    responseDto.setMood(response.getMood());
                    return responseDto;
                });
    }

    //일기 저장 api
    @PostMapping("/create/diary")
    void createDiary(@RequestBody DiaryVo diaryVo) {    //JSON 형식으로 요청

        diaryServiceImpl.createDiary(diaryVo);
        //moodRequestDto.setContent(diaryVo.getContent());
    }

    //일기 삭제 api
    @DeleteMapping("/delete/diary")
    void updateDiary(@RequestBody DeleteRequestDto deleteRequestDto) {
        diaryServiceImpl.deleteDiary(deleteRequestDto);
    }

    //일기 개별 조회 api
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestBody DiaryReadRequestDto diaryReadRequestDto){
        return diaryServiceImpl.readDiary(diaryReadRequestDto);
    }

    //일기 월별 조회 api
    @GetMapping("/read/diaries")
    List<Diary> readMonthlyDiary(@RequestBody DiaryMonthlyReadRequestDto diaryMonthlyReadRequestDto){
        return diaryServiceImpl.readMonthlyDiary(diaryMonthlyReadRequestDto);
    }
}
