package swu.musling.config.securityspring.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import swu.musling.membership.Member;

import java.util.Collection;

/**
 * 사용자가 시스템의 리소스에 접근하기 위해서는 먼저 인증 관리 필터의 검증을 통과해야함
 * 인증 관리 필터가 사용자가 입력한 정보대로 기능을 처리하기 위해서는 사용자 정보가 저장된 UserDetails 객체 필요
 * UserDetails 객체에 데이터베이스에서 검색한 사용자 정보를 저장하는 UserDetailsService 객체 필요
 * 인증 관리자는 UserDetailsService 객체를 통해 UserDetails 객체를 획득, UserDetails 객체에서 인증(Authentication)과 인가(Authorization)에 필요한 정보들을 추출하여 사용
 */
public class SecurityUser extends User {
    /*
    Member.java에 implements UserDetails를 하여 사용 해도 되지만
    Override 해야 할 추상 메소드들이 생기므로
    스프링이 제공하는 User 클래스를 상속하여 새로운 클래스를 정의하였습니다.
     */
    private Member member;

    public SecurityUser(Member member) {
        super(member.getId().toString(), member.getPwd(),
                AuthorityUtils.createAuthorityList(member.getRole().toString()));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
