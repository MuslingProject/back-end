package swu.musling.diary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DiaryController {
    private final DiaryService diaryService;
    MoodRequestDto moodRequestDto;
    private final MoodService moodService;

    public DiaryController(DiaryService diaryService, MoodService moodService) {
        this.diaryService = diaryService;
        this.moodService = moodService;
    }

    //일기 저장 api
    @PostMapping("/create/diary")
    void createDiary(@RequestBody DiaryVo diaryVo) {    //JSON 형식으로 요청

        diaryService.createDiary(diaryVo);
        moodRequestDto.setContent(diaryVo.getContent());
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
}
