package swu.musling.like.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swu.musling.like.dto.LikesCreateRequestDto;
import swu.musling.like.dto.LikesCreateResponseDto;
import swu.musling.like.dto.LikesResponseDto;
import swu.musling.like.jpa.LikesRepository;
import swu.musling.like.jpa.Likes;
import swu.musling.member.jpa.Member;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;

    @Autowired
    public LikesServiceImpl(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    @Override
    @Transactional
    public LikesCreateResponseDto saveLikes(LikesCreateRequestDto likesCreateRequestDto, Member member) {
        List<LikesCreateResponseDto.LikeInfo> likeInfos = likesCreateRequestDto.getLikes().stream()
                .map(likeData -> {
                    Likes like = Likes.builder()
                            .titles(likeData.getTitles())
                            .imgs(likeData.getImgs())
                            .singers(likeData.getSingers())
                            .emotion(likeData.getEmotion())
                            .weather(likeData.getWeather())
                            .member(member)
                            .build();
                    likesRepository.save(like);

                    return LikesCreateResponseDto.LikeInfo.builder()
                            .likeId(like.getLikesId())
                            .titles(like.getTitles())
                            .imgs(like.getImgs())
                            .singers(like.getSingers())
                            .emotion(like.getEmotion())
                            .weather(like.getWeather())
                            .build();
                })
                .collect(Collectors.toList());

        return LikesCreateResponseDto.builder()
                .likes(likeInfos)
                .build();
    }


    @Override
    @Transactional
    public void deleteLike(Integer likeId, Member member) {
        Likes like = likesRepository.findById(likeId)
                .orElseThrow(() -> new EntityNotFoundException("Like not found with id: " + likeId));

        if (!like.getMember().equals(member)) {
            throw new AccessDeniedException("You do not have permission to delete this like");
        }

        likesRepository.delete(like);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LikesResponseDto> getLikes(Member member) {
        List<Likes> likes = likesRepository.findByMember(member);
        return likes.stream()
                .map(LikesResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
