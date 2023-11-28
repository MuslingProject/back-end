package swu.musling.like.service;

import swu.musling.like.dto.LikeCreateRequestDto;
import swu.musling.like.dto.LikeCreateResponseDto;
import swu.musling.member.jpa.Member;

public interface LikeService {
    LikeCreateResponseDto saveLike(LikeCreateRequestDto likeCreateRequestDto, Member member);    //일기 찜하기
    void deleteLike(Integer likeId, Member member);
}
