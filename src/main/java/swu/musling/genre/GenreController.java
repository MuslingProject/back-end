package swu.musling.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swu.musling.ResponseDto;
import swu.musling.config.securityspring.config.SecurityUser;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/create/genre")
    public ResponseEntity create(@RequestBody GenreCreateRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) throws Exception{
        try {
            // 프론트에서 받은 토큰으로 Member 구분함
            requestDto.setMember(principal.getMember());
            ResponseDto responseDto = genreService.create(requestDto);
            return ResponseEntity.ok().body(ResponseDto.response(200, responseDto.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(400, e.getMessage()));
        }
    }
}
