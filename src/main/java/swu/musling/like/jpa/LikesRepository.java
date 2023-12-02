package swu.musling.like.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import swu.musling.member.jpa.Member;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
    List<Likes> findByMember(Member member);
}
