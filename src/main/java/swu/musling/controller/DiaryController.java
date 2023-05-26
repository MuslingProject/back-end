package swu.musling.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swu.musling.domain.Diary;
import swu.musling.domain.DiaryVo;
import swu.musling.service.DiaryService;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    //일기 저장 api
    @PostMapping("/create/diary")
    void createDiary(@RequestBody DiaryVo diaryVo) {    //JSON 형식으로 요청
        diaryService.createDiary(diaryVo);
    }
}
