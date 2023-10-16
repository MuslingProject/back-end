package swu.musling.music.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
    List<Music> findMusicByYearsAndGenres(String genre, Integer year);

    List<Music> findMusicByGenres(String genre);
}

