package swu.musling.member.jpa;

import lombok.*;
import swu.musling.genre.dto.GenreRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Integer profileId;    //자동 생성 id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;    //이미지 경로

    @Builder
    public Profile(Member member, String imageUrl) {
        this.member = member;
        this.imageUrl = imageUrl;
    }

    public void update(String imageUrl) {   //프로필 사진 업데이트 시 사용
        this.imageUrl = imageUrl;
    }
}
