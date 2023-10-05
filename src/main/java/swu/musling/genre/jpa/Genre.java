package swu.musling.genre.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swu.musling.genre.dto.GenreRequestDto;
import swu.musling.member.jpa.Member;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long genreId;    //자동 생성 id

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "indie", nullable = false)
    private boolean indie;    //인디
    @Column(name = "balad", nullable = false)
    private boolean balad;    //발라드
    @Column(name = "rock_metal", nullable = false)
    private boolean rockMetal;    //락메탈
    @Column(name = "dance_pop", nullable = false)
    private boolean dancePop;    //댄스팝
    @Column(name = "rap_hiphop", nullable = false)
    private boolean rapHiphop;    //랩힙합
    @Column(name = "rb_soul", nullable = false)
    private boolean rbSoul;    //알앤비소울
    @Column(name = "fork_acoustic", nullable = false)
    private boolean forkAcoustic;    //포크어쿠스틱

    @Builder
    public Genre(Member member, boolean indie, boolean balad,
                 boolean rockMetal, boolean dancePop,
                 boolean rapHiphop, boolean rbSoul, boolean forkAcoustic) {
        this.member = member;
        this.indie = indie;
        this.balad = balad;
        this.rockMetal = rockMetal;
        this.dancePop = dancePop;
        this.rapHiphop = rapHiphop;
        this.rbSoul = rbSoul;
        this.forkAcoustic = forkAcoustic;
    }

    //엔터티의 상태 변경을 한 곳에서 관리하기 위함
    public void update(GenreRequestDto genreRequestDto) {   //선호 장르 업데이트 시 사용
        this.indie = genreRequestDto.isIndie();
        this.balad = genreRequestDto.isBalad();
        this.rockMetal = genreRequestDto.isRockMetal();
        this.dancePop = genreRequestDto.isDancePop();
        this.rapHiphop = genreRequestDto.isRapHiphop();
        this.rbSoul = genreRequestDto.isRbSoul();
        this.forkAcoustic = genreRequestDto.isForkAcoustic();
    }
}
