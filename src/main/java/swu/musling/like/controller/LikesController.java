package swu.musling.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import swu.musling.ApiResponse;
import swu.musling.config.security.SecurityUser;
import swu.musling.like.dto.LikesCreateRequestDto;
import swu.musling.like.dto.LikesResponseDto;
import swu.musling.like.service.LikesService;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class LikesController {
    private final LikesService likesService;

    @Autowired
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    //노래 찜하기
    @PostMapping
    public ApiResponse<?> createLike(@RequestBody LikesCreateRequestDto likesCreateRequestDto,
                                     @AuthenticationPrincipal SecurityUser principal) {
        return ApiResponse.createSuccess(likesService.saveLike(likesCreateRequestDto, principal.getMember()));
    }

    //찜한 노래 취소
    @DeleteMapping("/{likeId}")
    public ApiResponse<?> deleteLike(@PathVariable Integer likeId,
                                     @AuthenticationPrincipal SecurityUser principal) {
        likesService.deleteLike(likeId, principal.getMember());
        return ApiResponse.createSuccess("노래 찜 취소 성공");
    }

    // 사용자가 찜한 노래 조회
    @GetMapping
    public ApiResponse<List<LikesResponseDto>> getLikes(@AuthenticationPrincipal SecurityUser principal) {
        return ApiResponse.createSuccess(likesService.getLikes(principal.getMember()));
    }
}
