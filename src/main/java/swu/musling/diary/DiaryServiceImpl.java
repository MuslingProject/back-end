package swu.musling.diary;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import swu.musling.membership.Member;
import swu.musling.membership.MemberRepository;

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
        //사용자가 저장을 누른다
        //인공지능 서버로 content를 보내고 모델 결과값을 받는다.
        //모델 결과값과 날씨에 따른 노래 랜덤 5개를 db에서 조회하고 보여준다. (메서드 호출 형식)
        //db에 저장한다. (list 형식으로)

        /*
        //Send user's content to Flask Server
        WebClient webclient1 = WebClient.builder().baseUrl("http://{ip}:5000").build();
        webclient1.post()
                .uri("/{api}")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(diarySaveRequestDto.getContent()))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
         */

        //Save the current mood_result to the DB
        Diary nowDiary = new Diary();
        UUID id = memberRepository.findByUserId(diarySaveRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id")).getId();
        //Member member = memberRepository.findById(id);

        //nowDiary.setMember(member);
        nowDiary.setTitle(diarySaveRequestDto.getTitle());
        nowDiary.setContent(diarySaveRequestDto.getContent());
        nowDiary.setDate(diarySaveRequestDto.getDate());
        nowDiary.setWeather(diarySaveRequestDto.getWeather());
        //nowDiary.setMember_id(member.getUser_id());
        //diaryRepository.save(nowDiary);

        /*
        //get the mood_result
        WebClient webclient2 = WebClient.builder().baseUrl("http://{ip}:5000").build();
        MoodResponseDto mood_result = webclient2.get()
                .uri("/{api}")
                .retrieve()
                .bodyToMono(MoodResponseDto.class)
                .block();

        nowDiary.setMood_result(String.valueOf(mood_result));
         */
        diaryRepository.save(nowDiary);
    }
}
