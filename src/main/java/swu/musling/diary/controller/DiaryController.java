package swu.musling.diary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import swu.musling.ApiResponse;
import swu.musling.config.security.SecurityUser;
import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.CreateDiaryResponseDto;
import swu.musling.diary.dto.DiaryResponseDto;
import swu.musling.diary.dto.EmotionCountResponseDto;
import swu.musling.diary.service.DiaryService;

import java.time.LocalDate;
import java.util.List;

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
        return ApiResponse.createSuccess(diaryService.getDiary(principal.getMember(), diaryId));
    }

    // 특정 날짜에 작성된 모든 일기 조회 요청 처리
    @GetMapping("/{date}")
    public ApiResponse<List<DiaryResponseDto>> getDiariesByDate(@AuthenticationPrincipal SecurityUser principal,
                                                                @PathVariable LocalDate date) {
        return ApiResponse.createSuccess(diaryService.getDiariesByDate(principal.getMember(), date));
    }

    /*
    Pageable pageable은 클라이언트(프론트엔드)로부터 페이지네이션과 관련된 데이터를 받는 데 사용됩니다. Pageable 인스턴스는 페이지 번호,
    페이지당 항목 수, 정렬 순서 등의 정보를 담고 있습니다.
    이 정보를 사용하여 서버 측에서는 요청된 페이지에 해당하는 데이터를 효율적으로 조회하고 반환할 수 있습니다.

    Pageable 사용 예시
    클라이언트가 서버에 데이터를 요청할 때, URL의 쿼리 파라미터를 통해 페이지네이션 정보를 전달합니다.
    예를 들어, size, page, sort 파라미터를 사용할 수 있습니다.
    페이지네이션은 웹과 앱 환경에서 모두 흔히 사용되는 기능입니다.

    page: 요청하는 페이지 번호 (0부터 시작)
    size: 한 페이지에 표시할 항목의 수
    sort: 정렬 기준 (예: sort=fieldName,asc 또는 sort=fieldName,desc)
     */
    //일기 전체 조회
    @GetMapping
    public ApiResponse<Page<DiaryResponseDto>> getAllDiaries(@AuthenticationPrincipal SecurityUser principal,
                                                             Pageable pageable) {
        return ApiResponse.createSuccess(diaryService.getAllDiaries(principal.getMember(), pageable));
    }

    //감정 개수 조회
    @GetMapping("/emotions")
    public ApiResponse<EmotionCountResponseDto> getEmotionCounts(@AuthenticationPrincipal SecurityUser principal) {
        return ApiResponse.createSuccess(diaryService.getEmotionCounts(principal.getMember()));
    }
}

