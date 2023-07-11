package swu.musling.config.securityspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swu.musling.membership.Member;
import swu.musling.membership.MemberRepository;

import java.util.Optional;

@Service
public class SecurityUserDetailService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    //id로 사용자 찾기
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optional = memberRepository.findByUserId(username);
        if(!optional.isPresent()) {
            throw new UsernameNotFoundException(username + " 사용자 없음");
        } else {
            Member member = optional.get();
            return new SecurityUser(member);
        }
    }
}
