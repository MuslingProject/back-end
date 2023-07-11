package swu.musling.diary;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class DiaryController {
    private final DiaryServiceImpl diaryServiceImpl;

    public DiaryController(DiaryServiceImpl diaryServiceImpl) {
        this.diaryServiceImpl = diaryServiceImpl;
    }

    //일기 저장 api
    @PostMapping("/create/diary")
    void createDiary(@RequestBody DiarySaveRequestDto diarySaveRequestDto) {    //JSON 형식으로 요청

        diaryServiceImpl.createDiary(diarySaveRequestDto);
    }
}
