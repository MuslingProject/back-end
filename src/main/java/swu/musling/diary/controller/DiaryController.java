package swu.musling.diary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import swu.musling.ApiResponse;
import swu.musling.config.security.SecurityUser;
import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.CreateDiaryResponseDto;
import swu.musling.diary.dto.DiaryResponseDto;
import swu.musling.diary.service.DiaryService;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
    private DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 일기 등록 요청 처리
    @PostMapping
    public ApiResponse<CreateDiaryResponseDto> createDiary(@AuthenticationPrincipal SecurityUser principal,
                                                           @RequestBody CreateDiaryRequestDto requestDto) {
        return ApiResponse.createSuccess(diaryService.createDiary(principal.getMember(), requestDto));
    }

    // 일기 삭제 요청 처리
    @DeleteMapping("/{diaryId}")
    public ApiResponse<?> deleteDiary(@AuthenticationPrincipal SecurityUser principal,
                                      @PathVariable Long diaryId) {
        diaryService.deleteDiary(principal.getMember(), diaryId);
        return ApiResponse.createSuccessWithNoData("일기 삭제 성공");
    }

    // 일기 개별 조회 요청 처리
    @GetMapping("/{diaryId}")
    public ApiResponse<DiaryResponseDto> getDiary(@AuthenticationPrincipal SecurityUser principal,
                                                  @PathVariable Long diaryId) {
        DiaryResponseDto diary = diaryService.getDiary(diaryId);
        return ApiResponse.createSuccess(diary);
    }

    //특정 날짜 일기 전체 조회
    //일기 전체 조회
    //감정 개수 조회

}

