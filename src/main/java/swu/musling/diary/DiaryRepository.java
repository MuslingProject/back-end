package swu.musling.diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swu.musling.diary.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}
