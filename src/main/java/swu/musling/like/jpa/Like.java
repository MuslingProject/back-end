//package swu.musling.like.jpa;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import swu.musling.member.jpa.Member;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@NoArgsConstructor
//@Table(name = "like")
//public class Like {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "like_id")
//    private Integer likeId;
//
//    @Column(name = "titles", nullable = false)
//    private String titles;
//    @Column(name = "imgs", nullable = false)
//    private String imgs;
//    @Column(name = "singers", nullable = false)
//    private String singers;
//    @Column(name = "emotion")
//    private String emotion;
//    @Column(name = "weather")
//    private String weather;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;
//
//    @Builder
//    public Like(String titles, String imgs, String singers, String emotion, String weather,
//                Member member) {
//        this.titles = titles;
//        this.imgs = imgs;
//        this.singers = singers;
//        this.emotion = emotion;
//        this.weather = weather;
//        this.member = member;
//    }
//}
