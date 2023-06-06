package swu.musling.membership;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    //id 컬럼을 member 테이블의 기본키로 설정
    @Id
    @GeneratedValue
    private UUID id;    //자동 생성 id
    private String userid;   //사용자 지정 id

    @Column(length = 100)
    private String pwd; //비밀번호
    private String name;    //이름
    private int age;    //연령대
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String userid) {
        this.userid = userid;
    }
}
