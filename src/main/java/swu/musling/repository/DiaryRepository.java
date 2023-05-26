package swu.musling.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swu.musling.domain.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}
