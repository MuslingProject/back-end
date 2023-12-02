package swu.musling.diary.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import swu.musling.diary.dto.*;
import swu.musling.diary.jpa.Diary;
import swu.musling.diary.jpa.DiaryRepository;
import swu.musling.diary.jpa.Recommendation;
import swu.musling.diary.jpa.RecommendationRepository;
import swu.musling.genre.jpa.Genre;
import swu.musling.member.jpa.Member;
import swu.musling.music.jpa.Category;
import swu.musling.music.jpa.CategoryRepository;
import swu.musling.music.jpa.Music;
import swu.musling.music.jpa.MusicRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DiaryServiceImpl implements DiaryService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private DiaryRepository diaryRepository;
    private RecommendationRepository recommendationRepository;
    private MusicRepository musicRepository;
    private CategoryRepository categoryRepository;
    private RestTemplate restTemplate;  // 인공지능 API 호출을 위한 RestTemplate


    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, RecommendationRepository recommendationRepository,
                            MusicRepository musicRepository, CategoryRepository categoryRepository,
                            RestTemplate restTemplate) {
        this.diaryRepository = diaryRepository;
        this.recommendationRepository = recommendationRepository;
        this.musicRepository = musicRepository;
        this.categoryRepository = categoryRepository;
        this.restTemplate = restTemplate;
    }


    /*
    노래를 추천하는 로직이 DiaryService에 국한된 로직이므로, 해당 서비스 내에서 캡슐화한다.
    하지만, 이 로직이 여러 곳에서 재사용될 가능성이 있다면 별도의 서비스로 분리할 예정
     */

    @Override
    @Transactional
    public CreateDiaryResponseDto createDiary(Member member, CreateDiaryRequestDto requestDto) {    //일기 등록

        // 1. 인공지능 API 호출
        EmotionDto emotion = getEmotionFromAI(requestDto);

        // Diary 객체를 데이터베이스에 저장합니다. 이 때, 기본 키가 생성됩니다.
        Diary diary = diaryRepository.save(Diary.builder()
                .title(requestDto.getTitle())
                .date(requestDto.getDate())
                .weather(requestDto.getWeather())
                .content(requestDto.getContent())
                .mood(emotion.getEmotion())
                .member(member)
                .build());


        // 2. 사용자 선호 장르 가져오기
        List<String> preferredGenres = getPreferredGenres(member);

        List<Recommendation> recommendations;
        if (member.isAgeRecommendation()) { // 2-1. 연령대 노래 추천을 받는 사용자라면
            // 3. true일 경우 emotion, age, genre, weather로 노래 추천
            recommendations = getMusicRecommendationsByEmotionAgeAndGenreAndWeather(emotion, preferredGenres, diary, member);
        } else {    // 2-2. 연령대 노래 추천을 받지 않는 사용자라면
            // 4. false일 경우 emotion, genre, weather로 노래 추천
            recommendations = getMusicRecommendationsByEmotionGenreAndWeather(emotion, preferredGenres, diary);
        }

        // 추천 객체들을 Diary에 추가합니다.
        recommendations.forEach(recommendation -> {
            diary.addRecommendation(
                    recommendation.getSongTitle(),
                    recommendation.getCoverImagePath(),
                    recommendation.getSinger(),
                    recommendation.getEmotion(),
                    recommendation.getWeather()
            );
        });
        // Diary와 연결된 모든 Recommendation 객체들은 CascadeType.ALL 설정으로 인해 자동으로 저장됩니다.
        // 추가적인 저장 로직은 필요하지 않습니다.

        return CreateDiaryResponseDto.fromEntity(diary);
    }

    @Override
    @Transactional
    public void deleteDiary(Member member, Long diaryId) { //일기 삭제
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("Diary with id " + diaryId + " not found"));

        // 사용자 확인
        if (!diary.getMember().getMemberId().equals(member.getMemberId())) {
            throw new AccessDeniedException("You are not authorized to delete this diary");
        }

        diaryRepository.delete(diary);
    }

    @Override
    @Transactional(readOnly = true)
    public DiaryResponseDto getDiary(Member member, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("Diary not found with id: " + diaryId));

        // 사용자 확인
        if (!diary.getMember().getMemberId().equals(member.getMemberId())) {
            throw new AccessDeniedException("You are not authorized to delete this diary");
        }

        return DiaryResponseDto.fromEntity(diary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaryResponseDto> getDiariesByDate(Member member, LocalDate date) {
        List<Diary> diaries = diaryRepository.findAllByMemberAndDate(member, date);
        return diaries.stream()
                .map(DiaryResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiaryResponseDto> getAllDiaries(Member member, Pageable pageable) {
        Page<Diary> diariesPage = diaryRepository.findAllByMember(member, pageable);
        return diariesPage.map(DiaryResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public EmotionCountResponseDto getEmotionCounts(Member member) {
        //회원의 모든 일기 조회
        List<Diary> diaries = diaryRepository.findAllByMember(member);
        //감정별 일기 수집 및 개수 계산
        Map<String, Long> emotionCounts = diaries.stream()  //일기를 스트림으로 변환
                /*
                collect(Collectors.groupingBy(Diary::getMood, Collectors.counting()))는 스트림의 각 Diary 객체에서 getMood() 메서드를 호출하여 감정(mood) 값을 가져옵니다.
                groupingBy는 이 감정 값을 기준으로 일기를 그룹화하고, counting() 컬렉터는 각 그룹에 속하는 일기의 수를 세어 Map<String, Long> 형태로 결과를 반환합니다.
                여기서 키(String)는 감정의 이름이고 값(Long)은 해당 감정을 가진 일기의 개수입니다.
                 */
                .collect(Collectors.groupingBy(Diary::getMood, Collectors.counting()));
        /*
        emotionCounts 맵의 엔트리셋(각각의 키-값 쌍) 중 값(Long)이 가장 큰 엔트리를 찾습니다. 이 값은 가장 자주 등장하는 감정의 빈도수를 나타냅니다.
        getKey() 메서드는 이 엔트리의 키, 즉 가장 자주 등장하는 감정의 이름을 반환합니다.
        emotionCounts.get(mostFrequentEmotion)는 이 감정의 이름을 사용하여 그 감정이 몇 번 등장했는지, 즉 빈도수를 찾습니다.
         */
        String mostFrequentEmotion = Collections.max(emotionCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
            Long count = emotionCounts.get(mostFrequentEmotion);

        return EmotionCountResponseDto.builder()
                .emotionCounts(emotionCounts)
                .mostFrequentEmotion(mostFrequentEmotion)
                .mostFrequentEmotionCount(count)
                .build();
    }

    @Override
    @Transactional
    public void reRecommendSongs(Long diaryId, EmotionDto emotionDto, Member member) {
        Diary diary = diaryRepository.findByDiaryIdAndMember(diaryId, member);

        List<String> preferredGenres = getPreferredGenres(member);


        List<Recommendation> newRecommendations;
        if (member.isAgeRecommendation()) { // 2-1. 연령대 노래 추천을 받는 사용자라면
            // 3. true일 경우 emotion, age, genre, weather로 노래 추천
            newRecommendations = getMusicRecommendationsByEmotionAgeAndGenreAndWeather(emotionDto, preferredGenres, diary, member);
        } else {    // 2-2. 연령대 노래 추천을 받지 않는 사용자라면
            // 4. false일 경우 emotion, genre, weather로 노래 추천
            newRecommendations = getMusicRecommendationsByEmotionGenreAndWeather(emotionDto, preferredGenres, diary);
        }

        // 기존 추천 목록 삭제
        diary.getRecommendations().clear();
        // 새로운 추천 목록 추가
        newRecommendations.forEach(newRecommendation ->
                diary.addRecommendation(
                        newRecommendation.getSongTitle(),
                        newRecommendation.getCoverImagePath(),
                        newRecommendation.getSinger(),
                        newRecommendation.getEmotion(),
                        newRecommendation.getWeather()
                )
        );
    }

    public List<String> getPreferredGenres(Member member) { //사용자 선호 장르
        Genre genre = member.getGenre(); // member에서 Genre 정보를 가져옴
        List<String> preferredGenres = new ArrayList<>();

        if (genre.isIndie()) preferredGenres.add("인디");
        if (genre.isBalad()) preferredGenres.add("발라드");
        if (genre.isRockMetal()) preferredGenres.add("락/메탈");
        if (genre.isDancePop()) preferredGenres.add("댄스/팝");
        if (genre.isRapHiphop()) preferredGenres.add("랩/힙합");
        if (genre.isRbSoul()) preferredGenres.add("알앤비/소울");
        if (genre.isForkAcoustic()) preferredGenres.add("포크/어쿠스틱");

        return preferredGenres;
    }

    private EmotionDto getEmotionFromAI(CreateDiaryRequestDto requestDto) {
        //1. API URL 설정(호출하려는 인공지능의 URL)
        String apiUrl = "http://abd1-34-16-162-185.ngrok.io /predict";
        //2. API 요청에서 사용할 HttpHeaders 설정
        //setContentType(MediaType.APPLICATION_JSON)은 요청 본문의 컨텐츠 타입이 JSON 형태임을 나타냄
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //3. HTTP 요청/응답의 본문과 헤더를 포함하는 HttpEntity 설정
        //ttpEntity 객체를 생성하고, 본문으로 Map.of("sentence", requestDto.getContent())을 넣어줍니다. 이것은 일기 내용을 sentence라는 키에 매핑하여 JSON 객체로 만듭니다.
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(Map.of("sentence", requestDto.getContent()), headers);
        //4. 인공지능 API 호출
        //여기서는 apiUrl에 POST 요청을 보내고, 요청 본문으로 entity를 전달합니다. 응답은 String.class 타입으로 받아와 emotion 변수에 저장한다.
        return restTemplate.postForObject(apiUrl, entity, EmotionDto.class);
    }

    //emotion, genre, weather로 노래 추천
    private List<Recommendation> getMusicRecommendationsByEmotionGenreAndWeather(EmotionDto emotion,
                                                                                 List<String> preferredGenres,
                                                                                 Diary diary) {
        List<Recommendation> recommendations = new ArrayList<>();

        // 감정에 맞는 추천 곡 가져오기
        recommendations.addAll(getMusicRecommendationsByEmotionAndGenre(emotion, preferredGenres, diary));

        // 로깅: 추출된 노래 목록 확인
        if (logger.isInfoEnabled()) {
            recommendations.forEach(music -> logger.info("Extracted Music for Weather Recommendation: {}", music.getEmotion()));
        }


        // 날씨에 맞는 추천 곡 가져오기
        List<Recommendation> weatherRecommendations = getMusicRecommendationsByWeather(diary.getWeather(), preferredGenres, diary);
        recommendations.addAll(weatherRecommendations);

        // 로깅: 추출된 노래 목록 확인
        if (logger.isInfoEnabled()) {
            recommendations.forEach(music -> logger.info("Extracted Music for Weather Recommendation: {}", music.getWeather()));
        }

        // 최대 6곡을 반환
        return recommendations.stream()
                .limit(6)
                .collect(Collectors.toList());
    }

    //age, emotion, genre, weather로 노래 추천
    @Transactional(readOnly = true)
    public List<Recommendation> getMusicRecommendationsByEmotionAgeAndGenreAndWeather(EmotionDto emotion,
                                                                                      List<String> preferredGenres,
                                                                                      Diary diary,
                                                                                      Member member) {
        int currentYear = Year.now().getValue();
        int age = parseAgeFromString(member.getAge()); // 연령대 문자열을 정수로 변환
        int yearRangeStart = getYearRangeStartForAge(age, currentYear);

        Category category = categoryRepository.findByName(diary.getWeather())
                .orElseThrow(() -> new IllegalArgumentException("Invalid weather: " + diary.getWeather()));

        List<Music> allMusics = new ArrayList<>();

        // 연령대에 맞는 년도 범위로 음악 검색
        for (String genre : preferredGenres) {
            List<Music> musics = musicRepository.findByGenresContainingAndYearsBetweenAndCategory(genre, yearRangeStart, currentYear, category);
            allMusics.addAll(musics);
        }

        Collections.shuffle(allMusics); // 전체 음악 리스트를 섞는다.

        List<Recommendation> ageGenreWeatherRecommendations = allMusics.stream()
                .limit(3) // 전체 리스트에서 상위 3곡만 추출
                .map(music -> convertToEmotionRecommendation(music, diary, emotion.getEmotion()))
                .collect(Collectors.toList());

        // 추천 리스트에 추가
        List<Recommendation> recommendations = new ArrayList<>(ageGenreWeatherRecommendations);

        // 날씨에 따른 음악도 추가
        List<Recommendation> weatherRecommendations = getMusicRecommendationsByWeather(diary.getWeather(), preferredGenres, diary);
        recommendations.addAll(weatherRecommendations);

        return recommendations.stream()
                .limit(6)
                .collect(Collectors.toList());
    }

    // Music 객체를 감정 기반의 추천 객체로 변환하는 메소드
    private Recommendation convertToEmotionRecommendation(Music music, Diary diary, String emotion) {
        return Recommendation.builder()
                .songTitle(music.getTitles())
                .coverImagePath(music.getImgs())
                .singer(music.getSingers())
                .emotion(emotion)
                .weather(null)
                // weather 필드는 설정하지 않음
                .diary(diary)
                .build();
    }

    // Music 객체를 날씨 기반의 추천 객체로 변환하는 메소드
    private Recommendation convertToWeatherRecommendation(Music music, Diary diary, String weather) {
        return Recommendation.builder()
                .songTitle(music.getTitles())
                .coverImagePath(music.getImgs())
                .singer(music.getSingers())
                // emotion 필드는 설정하지 않음
                .emotion(null)
                .weather(weather)
                .diary(diary)
                .build();
    }

    @Transactional(readOnly = true)
    // 감정과 장르에 따른 노래 추천 로직에 무작위 추출 로직 추가
    public List<Recommendation> getMusicRecommendationsByEmotionAndGenre(EmotionDto emotion,
                                                                         List<String> preferredGenres,
                                                                         Diary diary) {
        Category category = categoryRepository.findByName(emotion.getEmotion())
                .orElseThrow(() -> new IllegalArgumentException("Invalid emotion: " + emotion));

        List<Music> allMusicsByEmotion = new ArrayList<>();
        for (String genre : preferredGenres) {
            List<Music> musicsByGenre = musicRepository.findByGenresContainingAndCategory(genre, category);
            allMusicsByEmotion.addAll(musicsByGenre);
        }

        // 전체 리스트를 무작위로 섞음
        Collections.shuffle(allMusicsByEmotion);

        // 최대 3곡만 추출하여 추천 목록을 생성
        List<Recommendation> recommendations = allMusicsByEmotion.stream()
                .limit(3)
                .map(music -> convertToEmotionRecommendation(music, diary, emotion.getEmotion()))
                .collect(Collectors.toList());

        return recommendations;
    }

    @Transactional(readOnly = true)
    // 날씨에 맞는 추천 곡을 가져오는 메소드
    public List<Recommendation> getMusicRecommendationsByWeather(String weather,
                                                                  List<String> preferredGenres,
                                                                  Diary diary) {
        Category weatherCategory = categoryRepository.findByName(weather)
                .orElseThrow(() -> new IllegalArgumentException("Invalid weather: " + weather));

        List<Music> allMusicsByWeather = new ArrayList<>();
        for (String genre : preferredGenres) {
            List<Music> musicsByGenreAndWeather = musicRepository.findByGenresContainingAndCategory(genre, weatherCategory);
            allMusicsByWeather.addAll(musicsByGenreAndWeather);
        }

        Collections.shuffle(allMusicsByWeather); // 리스트를 무작위로 섞음

        List<Recommendation> recommendations = allMusicsByWeather.stream()
                .limit(3) // 최대 3곡
                .map(music -> convertToWeatherRecommendation(music, diary, weather))
                .collect(Collectors.toList());

        return recommendations;
    }

    // 연령대에 따른 시작 년도 계산 메소드
    /*
    10대: 최근 5년 내의 노래
    20대: 최근 10년 내의 노래
    30대: 최근 10년에서 20년 사이의 노래
    40대: 최근 20년에서 30년 사이의 노래
    50대: 최근 30년에서 40년 사이의 노래
     */
    private int getYearRangeStartForAge(int age, int currentYear) {
        int yearRangeStart;

        if (age < 20) {  // 10대
            yearRangeStart = currentYear - 5;
        } else if (age < 30) {  // 20대
            yearRangeStart = currentYear - 10;
        } else if (age < 40) {  // 30대
            yearRangeStart = currentYear - 20;
        } else if (age < 50) {  // 40대
            yearRangeStart = currentYear - 30;
        } else {  // 50대
            yearRangeStart = currentYear - 40;
        }

        return Math.max(yearRangeStart, 1980);  // 1980년 이전의 노래는 제외, 실제 상황에 따라 조정할 수 있음
    }

    // 문자열 연령대를 정수로 변환하는 메소드
    /*
     Java에서 제공하는 정규 표현식을 활용하여 문자열 중 숫자만을 추출하는 과정을 수행
     정규 표현식의 컴파일과 매칭 그리고 숫자의 추출과 변환

     정규 표현식 컴파일과 매칭:

    Pattern.compile("\\d+")는 "\d+"라는 정규 표현식을 컴파일합니다. 여기서 "\d"는 'digit(숫자)'를 의미하고 "+"는 '하나 또는 그 이상 연속되는'을 의미합니다. 그러므로 "\d+"는 '하나 이상의 연속된 숫자'를 찾는 패턴을 만듭니다.
    .matcher(ageString)은 ageString이라는 문자열에 이 패턴을 적용하여 Matcher 객체를 생성합니다. Matcher 객체는 문자열을 순회하면서 정규 표현식과 일치하는 부분을 찾는 기능을 수행합니다.
    숫자의 추출과 변환:

    matcher.find()는 ageString 내에서 정규 표현식에 일치하는 부분을 찾습니다. 만약 일치하는 부분이 있다면 true를 반환하고, 일치하는 부분이 없다면 false를 반환합니다.
    Integer.parseInt(matcher.group())는 matcher.find()에 의해 찾아진 일치하는 문자열(이 경우는 연속된 숫자)을 추출하여 (matcher.group()) 정수형으로 변환합니다 (Integer.parseInt).
    * 10은 추출된 숫자가 "10"이면 "10대"를 의미하고, "20"이면 "20대"를 의미하는 식으로, 실제 연령대를 나타내는 숫자로 변환하기 위해 사용됩니다. 즉, "20"이 추출되면 실제로는 "2000년대 출생"을 의미하므로 10을 곱해주는 것입니다.
     */
    private int parseAgeFromString(String ageString) {
        // 정규 표현식을 사용하여 문자열에서 숫자만 추출
        Matcher matcher = Pattern.compile("\\d+").matcher(ageString);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group()) * 10; // "10대" -> 10, "20대" -> 20
        }
        throw new IllegalArgumentException("Invalid age format: " + ageString);
    }
}
