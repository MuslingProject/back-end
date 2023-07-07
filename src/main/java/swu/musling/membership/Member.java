package swu.musling.membership;

import lombok.*;
import swu.musling.diary.Diary;
import swu.musling.genre.Genre;

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
    @Column(name = "id")
    private UUID id;    //자동 생성 id
    private String userid;   //사용자 지정 id

    @Column(length = 100)
    private String pwd; //비밀번호
    private String name;    //이름
    private String age;    //연령대
    @Enumerated(EnumType.STRING)
    private Role role;
    private String imageUrl;    //프로필 경로
    @OneToMany(mappedBy = "member")
    private List<Genre> genres = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();

    @Builder
    public Member(String userid) {
        this.userid = userid;
    }
}
