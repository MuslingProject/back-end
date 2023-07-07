package swu.musling.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/create/genre")
    public void create(@RequestBody GenreCreateRequestDto requestDto) {
        genreService.create(requestDto);
    }
}
