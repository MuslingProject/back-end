package swu.musling.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swu.musling.diary.Diary;
import swu.musling.membership.Member;

import java.time.LocalDate;
import java.util.List;

//변수명 이름 순서에 주의해야함(이름 순서대로 인식)
@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    //void deleteAllByMember_idAndDate(String member_id, LocalDate localDate);
}
