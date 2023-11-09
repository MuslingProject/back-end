package swu.musling.diary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    // 일기 등록 요청 처리
    @PostMapping("/diaries")
    public ApiResponse<CreateDiaryResponseDto> createDiary(@AuthenticationPrincipal SecurityUser principal,
                                                           @RequestBody CreateDiaryRequestDto requestDto) {
        return ApiResponse.createSuccess(diaryService.createDiary(principal.getMember(), requestDto));
    }

    // 일기 삭제 요청 처리
    @DeleteMapping("/{diaryId}")
    public ApiResponse<?> deleteDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return ApiResponse.createSuccessWithNoData("일기 삭제 성공");
    }
}

