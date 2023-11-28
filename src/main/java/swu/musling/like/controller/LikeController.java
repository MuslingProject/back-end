package swu.musling.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import swu.musling.ApiResponse;
import swu.musling.config.security.SecurityUser;
import swu.musling.like.dto.LikeCreateRequestDto;
import swu.musling.like.service.LikeService;

@RestController
@RequestMapping("/songs")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    //노래 찜하기
    @PostMapping
    public ApiResponse<?> createLike(@RequestBody LikeCreateRequestDto likeCreateRequestDto,
                                     @AuthenticationPrincipal SecurityUser principal) {
        return ApiResponse.createSuccess(likeService.saveLike(likeCreateRequestDto, principal.getMember()));
    }

    //찜한 노래 취소
    @DeleteMapping("/{likeId}")
    public ApiResponse<?> deleteLike(@PathVariable Integer likeId,
                                     @AuthenticationPrincipal SecurityUser principal) {
        likeService.deleteLike(likeId, principal.getMember());
        return ApiResponse.createSuccess("노래 찜 취소 성공");
    }
}
