//package swu.musling.like.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import swu.musling.like.dto.LikeCreateRequestDto;
//import swu.musling.like.dto.LikeCreateResponseDto;
//import swu.musling.like.jpa.Like;
//import swu.musling.like.jpa.LikeRepository;
//import swu.musling.member.jpa.Member;
//
//import javax.persistence.EntityNotFoundException;
//
//@Service
//public class LikeServiceImpl implements LikeService{
//    private final LikeRepository likeRepository;
//
//    @Autowired
//    public LikeServiceImpl(LikeRepository likeRepository) {
//        this.likeRepository = likeRepository;
//    }
//
//    @Override
//    @Transactional
//    public LikeCreateResponseDto saveLike(LikeCreateRequestDto likeCreateRequestDto, Member member) {
//        Like like = Like.builder()
//                .titles(likeCreateRequestDto.getTitles())
//                .imgs(likeCreateRequestDto.getImgs())
//                .singers(likeCreateRequestDto.getSingers())
//                .emotion(likeCreateRequestDto.getEmotion())
//                .weather(likeCreateRequestDto.getWeather())
//                .member(member)
//                .build();
//        likeRepository.save(like);
//
//        return LikeCreateResponseDto.builder()
//                .likeIds(like.getLikeId())
//                .build();
//    }
//
//    @Override
//    @Transactional
//    public void deleteLike(Integer likeId, Member member) {
//        Like like = likeRepository.findById(likeId)
//                .orElseThrow(() -> new EntityNotFoundException("Like not found with id: " + likeId));
//
//        if (!like.getMember().equals(member)) {
//            throw new AccessDeniedException("You do not have permission to delete this like");
//        }
//
//        likeRepository.delete(like);
//    }
//}
