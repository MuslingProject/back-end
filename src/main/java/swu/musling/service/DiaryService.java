package swu.musling.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DiaryService {
    @Value("${openweathermap.key}") //스프링부트에 이미 지정되어 있는 변수를 가져온다. (application.properties 설정)
    private String apiKey;

    public void createDiary(LocalDate date, String text) {
        //open weater map 에서 데이터 받아오기
        //받아온 날씨 데이터 파싱하기
        //db에 저장하기

        getWeatherString();
    }

    private String getWeatherString() {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;
        System.out.println(apiUrl);
        return "";
    }
}
