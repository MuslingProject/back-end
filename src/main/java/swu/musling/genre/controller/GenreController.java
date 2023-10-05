package swu.musling.genre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import swu.musling.ApiResponse;
import swu.musling.config.security.SecurityUser;
import swu.musling.genre.dto.GenreRequestDto;
import swu.musling.genre.dto.GenreResponseDto;
import swu.musling.genre.service.GenreService;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;
    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ApiResponse<GenreResponseDto> getGenre(@AuthenticationPrincipal SecurityUser principal) {    //장르 조회
        return ApiResponse.createSuccess(genreService.getGenre(principal.getMember()));
    }

    @PostMapping
    public ApiResponse<GenreResponseDto> saveGenre(@AuthenticationPrincipal SecurityUser principal,
                                                   @RequestBody GenreRequestDto genreRequestDto) {  //장르 저장
        return ApiResponse.createSuccess(genreService.saveGenre(principal.getMember(), genreRequestDto));
    }

    @PutMapping
    public ApiResponse<GenreResponseDto> updateGenre(@AuthenticationPrincipal SecurityUser principal,
                                                     @RequestBody GenreRequestDto genreRequestDto) {    //장르 수정
        return ApiResponse.createSuccess(genreService.updateGenre(principal.getMember(), genreRequestDto));
    }
}
