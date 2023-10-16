package swu.musling.member.dto;

import lombok.Builder;
import lombok.Data;
import swu.musling.genre.dto.GenreResponseDto;
import swu.musling.genre.jpa.Genre;
import swu.musling.member.jpa.Member;

@Data
public class MemberResponseDto {
    private String profileImageUrl;
    private String name;
    private String age;
    private boolean ageRecommendation;
    private GenreResponseDto preferredGenres;

    @Builder
    public MemberResponseDto(String profileImageUrl, String name, String age, boolean ageRecommendation, Genre genre) {
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.age = age;
        this.ageRecommendation = ageRecommendation;
        this.preferredGenres = GenreResponseDto.of(genre);
    }

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .profileImageUrl(member.getProfile().getImageUrl())
                .name(member.getName())
                .age(member.getAge())
                .ageRecommendation(member.isAgeRecommendation())
                .genre(member.getGenre())
                .build();
    }
}
