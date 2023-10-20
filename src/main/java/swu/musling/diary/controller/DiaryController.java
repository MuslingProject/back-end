package swu.musling.diary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swu.musling.ApiResponse;
import swu.musling.config.security.SecurityUser;
import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.CreateDiaryResponseDto;
import swu.musling.diary.service.DiaryService;

@RestController
public class DiaryController {
    private DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diaries")
    public ApiResponse<CreateDiaryResponseDto> createDiary(@AuthenticationPrincipal SecurityUser principal,
                                                           @RequestBody CreateDiaryRequestDto requestDto) {  //일기 등록
        return ApiResponse.createSuccess(diaryService.createDiary(principal.getMember(), requestDto));
    }
}

