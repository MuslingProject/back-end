package swu.musling.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByMember_Id(UUID id);
}

