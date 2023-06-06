package swu.musling.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    @Transactional
    public Long create(GenreCreateRequestDto requestDto) {
        return genreRepository.save(requestDto.toEntity()).getGenreid();
    }
}
