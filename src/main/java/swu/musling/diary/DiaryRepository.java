package swu.musling.diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swu.musling.diary.Diary;

import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    @Transactional
    void deleteAllByUserIdAndDate(String userId, LocalDate date);
    //변수명 이름 순서에 주의해야함(이름 순서대로 인식)
}
