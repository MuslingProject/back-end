package swu.musling.diary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import swu.musling.diary.dto.CreateDiaryRequestDto;
import swu.musling.diary.dto.DiaryResponseDto;
import swu.musling.diary.dto.EmotionResponseDto;
import swu.musling.diary.jpa.Diary;
import swu.musling.diary.jpa.DiaryRepository;
import swu.musling.diary.jpa.Recommendation;
import swu.musling.diary.jpa.RecommendationRepository;
import swu.musling.genre.jpa.Genre;
import swu.musling.member.jpa.Member;
import swu.musling.music.jpa.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final RestTemplate restTemplate;

    private final MusicRepository musicRepository;

    private final RecommendationRepository recommendationRepository;
    private final MoodAnxietyMusicRepository anxietyMusicRepository;
    private final MoodGloomMusicRepository gloomMusicRepository;
    private final MoodHappyMusicRepository happyMusicRepository;
    private final MoodSadMusicRepository sadMusicRepository;
    private final MoodStressMusicRepository stressMusicRepository;
    private final WeatherRainMusicRepository rainMusicRepository;
    private final WeatherSnowMusicRepository snowMusicRepository;
    private final WeatherSunnyMusicRepository sunnyMusicRepository;

    private final Map<String, MusicRepository> emotionToRepositoryMap;
    private final Map<String, MusicRepository> weatherToRepositoryMap;
    private final Map<String, String> genreToColumnMap;
    private final Map<String, Integer> ageToYearMap;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, RestTemplate restTemplate,
                            MusicRepository musicRepository,
                            RecommendationRepository recommendationRepository,
                            MoodAnxietyMusicRepository anxietyMusicRepository,
                            MoodGloomMusicRepository gloomMusicRepository,
                            MoodHappyMusicRepository happyMusicRepository,
                            MoodSadMusicRepository sadMusicRepository,
                            MoodStressMusicRepository stressMusicRepository,
                            WeatherRainMusicRepository rainMusicRepository,
                            WeatherSnowMusicRepository snowMusicRepository,
                            WeatherSunnyMusicRepository sunnyMusicRepository) {
        this.diaryRepository = diaryRepository;
        this.restTemplate = restTemplate;
        this.musicRepository = musicRepository;
        this.recommendationRepository = recommendationRepository;
        this.anxietyMusicRepository = anxietyMusicRepository;
        this.gloomMusicRepository = gloomMusicRepository;
        this.happyMusicRepository = happyMusicRepository;
        this.sadMusicRepository = sadMusicRepository;
        this.stressMusicRepository = stressMusicRepository;
        this.rainMusicRepository = rainMusicRepository;
        this.snowMusicRepository = snowMusicRepository;
        this.sunnyMusicRepository = sunnyMusicRepository;

        emotionToRepositoryMap = Map.of(
                "사랑/기쁨", happyMusicRepository,
                "멘붕/불안", anxietyMusicRepository,
                "이별/슬픔", sadMusicRepository,
                "스트레스/짜증", stressMusicRepository,
                "우울", gloomMusicRepository
        );

        weatherToRepositoryMap = Map.of(
                "비/흐림", rainMusicRepository,
                "눈오는 날", snowMusicRepository,
                "화창한 날", sunnyMusicRepository
        );

        genreToColumnMap = Map.of(
                "인디", "indie",
                "발라드", "balad",
                "락/메탈", "rock_metal",
                "댄스/팝", "dance_pop",
                "랩/힙합", "rap_hiphop",
                "알앤비/소울", "rb_soul",
                "포크/어쿠스틱", "fork_acoustic"
        );

        ageToYearMap = Map.of(
                "10대", 2010,
                "20대", 2000,
                "30대", 1990,
                "40대", 1980,
                "50대", 1970
        );
    }


    @Override
    @Transactional
    public DiaryResponseDto createDiary(Member member, CreateDiaryRequestDto requestDto) {

        // 1. 인공지능 API 호출
        EmotionResponseDto emotion = getEmotionFromAI(requestDto);

        // Diary 객체 먼저 생성
        Diary diary = Diary.builder()
                .title(requestDto.getTitle())
                .date(requestDto.getDate())
                .weather(requestDto.getWeather())
                .content(requestDto.getContent())
                .mood(emotion.getEmotion())
                .member(member)
                .build();

        // 2. 사용자의 ageRecommendation 체크
        List<Recommendation> recommendations;

        List<String> preferredGenres = getPreferredGenres(member); // 사용자의 선호 장르 가져오기

        if (member.isAgeRecommendation()) {
            // 3. true일 경우 emotion, age, genre로 노래 추천
            recommendations = getMusicRecommendationsByEmotionAgeAndGenre(emotion, member, preferredGenres, diary);
        } else {
            // 4. false일 경우 emotion, genre로 노래 추천
            recommendations = getMusicRecommendationsByEmotionAndGenre(emotion, preferredGenres, diary);
        }

        // 5. 날씨, genre에 따른 노래 추천 추가
        recommendations.addAll(getWeatherBasedMusicRecommendationsByWeatherAndGenre(requestDto, preferredGenres, diary));

        // 6. 일기 및 노래 추천 저장
        Diary savedDiary = saveDiaryAndRecommendations(requestDto, emotion, member, recommendations);

        return convertToResponseDto(savedDiary);
    }

    public List<String> getPreferredGenres(Member member) {
        Genre genre = member.getGenre(); // member에서 Genre 정보를 가져옴
        List<String> preferredGenres = new ArrayList<>();

        if (genre.isIndie()) preferredGenres.add("indie");
        if (genre.isBalad()) preferredGenres.add("balad");
        if (genre.isRockMetal()) preferredGenres.add("rockMetal");
        if (genre.isDancePop()) preferredGenres.add("dancePop");
        if (genre.isRapHiphop()) preferredGenres.add("rapHiphop");
        if (genre.isRbSoul()) preferredGenres.add("rbSoul");
        if (genre.isForkAcoustic()) preferredGenres.add("folkAcoustic");

        return preferredGenres;
    }

    private List<Recommendation> getMusicRecommendationsByEmotionAgeAndGenre(EmotionResponseDto emotion, Member member,
                                                                             List<String> preferredGenres,
                                                                             Diary diary) {
        List<Recommendation> recommendations = new ArrayList<>();

        // emotion에 매핑되는 레포지토리 가져오기
        MusicRepository repository = emotionToRepositoryMap.get(emotion.getEmotion());


        // 사용자의 연령에 맞는 년도 가져오기
        Integer year = ageToYearMap.get(member.getAge());

        // 사용자가 선택한 각 선호 장르에 대해 노래 추천
        for (String genre : preferredGenres) {
            // 장르에 매핑되는 컬럼 이름 가져오기
            String column = genreToColumnMap.get(genre);

            // emotion, age, genre를 기반으로 DB에서 노래 추천 (이 부분은 구체적인 구현에 따라 달라질 수 있음)
            List<Music> genreBasedMusic  = repository.findMusicByYearsAndGenres(column, year);

            // Music 객체를 Recommendation 객체로 변환
            List<Recommendation> genreBasedRecommendations = genreBasedMusic.stream()
                    .map(music -> Recommendation.builder()
                            .songTitle(music.getTitles())
                            .coverImagePath(music.getImgs())
                            .singer(music.getSingers())
                            .emotion(emotion.getEmotion())
                            .diary(diary)  // 여기에서 Diary 객체를 설정합니다.
                            .build())
                    .collect(Collectors.toList());

            // 랜덤하게 3개의 추천 노래만 저장
            Collections.shuffle(genreBasedRecommendations);
            recommendations.addAll(genreBasedRecommendations.subList(0, Math.min(3, genreBasedRecommendations.size())));

        }

        return recommendations;
    }

    private List<Recommendation> getMusicRecommendationsByEmotionAndGenre(EmotionResponseDto emotion,
                                                                          List<String> preferredGenres, Diary diary) {
        List<Recommendation> recommendations = new ArrayList<>();

        // emotion에 매핑되는 레포지토리 가져오기
        MusicRepository repository = emotionToRepositoryMap.get(emotion.getEmotion());


        // 사용자가 선택한 각 선호 장르에 대해 노래 추천
        for (String genre : preferredGenres) {
            // 장르에 매핑되는 컬럼 이름 가져오기
            String column = genreToColumnMap.get(genre);

            // emotion, age, genre를 기반으로 DB에서 노래 추천 (이 부분은 구체적인 구현에 따라 달라질 수 있음)
            List<Music> genreBasedMusic  = repository.findMusicByGenres(column);

            // Music 객체를 Recommendation 객체로 변환
            List<Recommendation> genreBasedRecommendations = genreBasedMusic.stream()
                    .map(music -> Recommendation.builder()
                            .songTitle(music.getTitles())
                            .coverImagePath(music.getImgs())
                            .singer(music.getSingers())
                            .emotion(emotion.getEmotion())
                            .diary(diary)  // 여기에서 Diary 객체를 설정합니다.
                            .build())
                    .collect(Collectors.toList());

            // 랜덤하게 3개의 추천 노래만 저장
            Collections.shuffle(genreBasedRecommendations);
            recommendations.addAll(genreBasedRecommendations.subList(0, Math.min(3, genreBasedRecommendations.size())));

        }

        return recommendations;
    }

    private List<Recommendation> getWeatherBasedMusicRecommendationsByWeatherAndGenre(CreateDiaryRequestDto requestDto,
                                                                                      List<String> preferredGenres,
                                                                                      Diary diary) {
        List<Recommendation> recommendations = new ArrayList<>();

        // weather에 매핑되는 레포지토리 가져오기
        MusicRepository repository = weatherToRepositoryMap.get(requestDto.getWeather());


        // 사용자가 선택한 각 선호 장르에 대해 노래 추천
        for (String genre : preferredGenres) {
            // 장르에 매핑되는 컬럼 이름 가져오기
            String column = genreToColumnMap.get(genre);

            // emotion, age, genre를 기반으로 DB에서 노래 추천 (이 부분은 구체적인 구현에 따라 달라질 수 있음)
            List<Music> genreBasedMusic  = repository.findMusicByGenres(column);

            // Music 객체를 Recommendation 객체로 변환 (이 부분은 실제 구현에 따라 변경될 수 있음)
            List<Recommendation> genreBasedRecommendations = genreBasedMusic.stream()
                    .map(music -> Recommendation.builder()
                            .songTitle(music.getTitles())
                            .coverImagePath(music.getImgs())
                            .singer(music.getSingers())
                            .weather(requestDto.getWeather())
                            .diary(diary)  // 여기에서 Diary 객체를 설정합니다.
                            .build())
                    .collect(Collectors.toList());

            // 랜덤하게 3개의 추천 노래만 저장
            Collections.shuffle(genreBasedRecommendations);
            recommendations.addAll(genreBasedRecommendations.subList(0, Math.min(3, genreBasedRecommendations.size())));

        }

        return recommendations;
    }

    private EmotionResponseDto getEmotionFromAI(CreateDiaryRequestDto requestDto) {
        // 인공지능 API 호출
        String apiUrl = "http://e54e-34-125-244-75.ngrok.io/predict";  // 인공지능 API URL을 여기에 작성합니다.

        //HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();    //API 요청에서 사용할 HTTP 헤더를 설정
        headers.setContentType(MediaType.APPLICATION_JSON); //요청 본문의 컨텐츠 타입이 JSON 형태임을 나타낸다.
        //HttpEntity 설정
        //HttpEntity는 HTTP 요청/응답의 본문과 헤더를 포함하는 객체
        /**
         여기서는 HttpEntity 객체를 생성하고, 본문으로 Map.of("sentence", requestDto.getContent())을 넣어줍니다.
         이것은 일기 내용을 sentence라는 키에 매핑하여 JSON 객체로 만듭니다.
         또한, 위에서 설정한 headers를 HttpEntity에 넣어줍니다.
         **/
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(Map.of("sentence", requestDto.getContent()), headers);

        //restTemplate.postForObject() 메서드는 POST 요청을 보내고, 응답을 받아와 지정된 클래스 타입으로 변환
        //여기서는 apiUrl에 POST 요청을 보내고, 요청 본문으로 entity를 전달합니다. 응답은 String.class 타입으로 받아와 emotion 변수에 저장
        return restTemplate.postForObject(apiUrl, entity, EmotionResponseDto.class);
    }

    private Diary saveDiaryAndRecommendations(CreateDiaryRequestDto requestDto, EmotionResponseDto emotion, Member member,
                                              List<Recommendation> recommendations) {
        Diary diary = Diary.builder()
                .title(requestDto.getTitle())
                .date(requestDto.getDate())
                .weather(requestDto.getWeather())
                .content(requestDto.getContent())
                .mood(emotion.getEmotion())
                .member(member)
                .build();

        diary = diaryRepository.save(diary);

        // Recommendations도 저장
        recommendations.forEach(recommendationRepository::save);

        return diary;
    }
    private DiaryResponseDto convertToResponseDto(Diary diary) {
        return DiaryResponseDto.builder()
                .title(diary.getTitle())
                .date(diary.getDate())
                .weather(diary.getWeather())
                .content(diary.getContent())
                .mood(diary.getMood())
                .build();
    }
}

