package swu.musling.like.service;

import swu.musling.like.dto.LikesCreateRequestDto;
import swu.musling.like.dto.LikesCreateResponseDto;
import swu.musling.like.dto.LikesResponseDto;
import swu.musling.member.jpa.Member;

import java.util.List;

public interface LikesService {
    LikesCreateResponseDto saveLikes(LikesCreateRequestDto likesCreateRequestDto, Member member);    //일기 찜하기
    void deleteLike(Integer likeId, Member member);
    List<LikesResponseDto> getLikes(Member member);
}
