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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    //id 컬럼을 member 테이블의 기본키로 설정
    @Id
    @GeneratedValue
    @Column(name = "member_id", columnDefinition = "BINARY(16)")
    private UUID id;    //자동 생성 id
    @Column(length = 100, unique = true, nullable = false)
    private String userId;   //사용자 지정 id
    @Column(length = 100, unique = true)
    private String pwd; //비밀번호

    private String name;    //이름
    private String age;    //연령대
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "member")
    private List<Genre> genres = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();

}
