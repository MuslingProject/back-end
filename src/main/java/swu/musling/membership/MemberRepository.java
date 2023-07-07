package swu.musling.membership;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUserid(String userid);
    Member findById(UUID id);
}
