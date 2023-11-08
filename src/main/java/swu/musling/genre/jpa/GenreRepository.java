package swu.musling.genre.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swu.musling.member.jpa.Member;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    //null이 올 수 있는 값을 감싸는 Wrapper 클래스로, 참조하더라도 NPE가 발생하지 않도록 도와준다.
    Optional<Genre> findByMember(Member member);
}
