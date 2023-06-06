package swu.musling.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import swu.musling.genre.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

