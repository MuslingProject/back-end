package swu.musling.member.jpa;

import lombok.*;
import swu.musling.BaseTimeEntity;
import swu.musling.diary.jpa.Diary;
import swu.musling.genre.jpa.Genre;
import swu.musling.like.jpa.Likes;
import swu.musling.member.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseTimeEntity {
    //id 컬럼을 member 테이블의 기본키로 설정
    @Id
    @GeneratedValue
    @Column(name = "member_id", columnDefinition = "BINARY(16)")
    private UUID memberId;    //자동 생성 id
    @Column(name = "email", nullable = false, unique = true)
    private String email;   //사용자 지정 id
    @Column(name = "pwd")
    private String pwd; //비밀번호
    @Column(name = "name", nullable = false)
    private String name;    //이름
    @Column(name = "age", nullable = false)
    private String age;    //연령대
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;  //권한
    @Column(name = "age_recommendation", nullable = false)
    private boolean ageRecommendation;    //연령대에 따른 노래 추천 on/off

    //대상 테이블(profile)에 외래키 양방향
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Profile profile;
    //대상 테이블(genre)에 외래키 양방향
    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    private Genre genre;

    // 1:n 관계 설정
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();

    // 1:n 관계 설정
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();


    @Builder
    public Member(String email, String pwd, String name, String age, Role role, boolean ageRecommendation) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.role = role;
        this.ageRecommendation = ageRecommendation;
    }

    public void updateName(String name) {   //별명 업데이트 시 사용
        this.name = name;
    }

    public void updateAgeRecommendation(boolean ageRecommendation) {    //연령대 추천 on/off 업데이트 시 사용
        this.ageRecommendation = ageRecommendation;
    }


}
