package swu.musling.diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swu.musling.diary.Diary;

import java.time.LocalDate;
import java.util.List;

//변수명 이름 순서에 주의해야함(이름 순서대로 인식)
@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    @Transactional //DB에 영향을 주는 메서드는 붙여주기
    void deleteAllByUserIdAndDate(String userId, LocalDate date);
    List<Diary> findAllByUserIdAndDate(String userId, LocalDate date);
    List<Diary> findAllByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);
}
