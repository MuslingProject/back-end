package swu.musling.diary;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
//SpringBoot의 HTTP API요청을 위한 메서드로 요청 Request에 데이터를 담아 Flask(본 글에서는 Test Controller)으로 요청을 보냅니다.
public class  MoodService {
    private final WebClient webClient;
    private final DiaryRepository diaryRepository;

    public MoodService(WebClient.Builder webClientBuilder, DiaryRepository diaryRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
        this.diaryRepository = diaryRepository;
    }

    public Mono<String> sendDiary(String diary) {
        MoodRequestDto requestDto = new MoodRequestDto();
        requestDto.setContent(diary);

        // Flask 서버의 '/receive_diary' 엔드포인트로 일기 내용을 전송
        return webClient.post().uri("/receive_diary")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<MoodResponseDto> getMood() {

        // Flask 서버의 '/get_mood' 엔드포인트로 모델 결과값을 요청
        return webClient.get().uri("/get_mood")
                .retrieve()
                .bodyToMono(MoodResponseDto.class);
    }
}
