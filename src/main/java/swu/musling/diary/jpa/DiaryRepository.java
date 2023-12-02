package swu.musling.diary.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swu.musling.member.jpa.Member;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByMemberAndDate(Member member, LocalDate date);
    Page<Diary> findAllByMember(Member member, Pageable pageable);
    List<Diary> findAllByMember(Member member);
    Diary findByDiaryIdAndMember(Long diaryId, Member member);
}
