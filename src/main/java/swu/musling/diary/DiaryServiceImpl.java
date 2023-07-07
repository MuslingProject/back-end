package swu.musling.diary;

import org.springframework.stereotype.Service;
import swu.musling.membership.Member;
import swu.musling.membership.MemberRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DiaryServiceImpl implements DiaryService{
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    public DiaryServiceImpl(DiaryRepository diaryRepository, MemberRepository memberRepository) {
        this.diaryRepository = diaryRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void createDiary(DiarySaveRequestDto diarySaveRequestDto) {
        //일기 값 db에 넣기
        Diary nowDiary = new Diary();
        UUID id = memberRepository.findByUserid(diarySaveRequestDto.getUserId()).get().getId();
        Member member = memberRepository.findById(id);

        nowDiary.setMember(member);
        nowDiary.setTitle(diarySaveRequestDto.getTitle());
        nowDiary.setContent(diarySaveRequestDto.getContent());
        nowDiary.setDate(diarySaveRequestDto.getDate());
        nowDiary.setWeather(diarySaveRequestDto.getWeather());
        nowDiary.setMusicTitle(diarySaveRequestDto.getMusicTitle());
        nowDiary.setMusicSinger(diarySaveRequestDto.getMusicSinger());
        nowDiary.setMusicImg(diarySaveRequestDto.getMusicImg());
        nowDiary.setMood_result(diarySaveRequestDto.getMood_result());
        diaryRepository.save(nowDiary);
    }
}
